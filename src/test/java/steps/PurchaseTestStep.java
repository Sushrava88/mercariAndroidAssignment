package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.androidscreens.CartScreen;
import org.example.androidscreens.CheckoutScreen;
import org.example.androidscreens.LoginScreen;
import org.example.context.TestContext;
import org.example.utils.CommonUtils;
import org.example.utils.ConfigFileReader;
import org.example.utils.MobileActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class PurchaseTestStep {

    TestContext testContext;
    LoginScreen loginScreen;
    CheckoutScreen checkoutScreen;
    CartScreen cartScreen;

    public PurchaseTestStep(TestContext testContext) {
        this.testContext = testContext;
        loginScreen = testContext.getAndroidPage().getLoginScreen();
        checkoutScreen = testContext.getAndroidPage().getCheckoutScreen();
        cartScreen = testContext.getAndroidPage().getCartScreen();
    }


        @Given("the user logs in with valid credentials")
        public void theUserLogsInWithValidCredentials() {

        // Read credentials from configuration
            String username = CommonUtils.getProperty("username");
            String password = CommonUtils.getProperty("password");
            loginScreen.login(username, password);
            Assert.assertTrue(loginScreen.isProductPageDisplayed(), "Product page is not displayed.");

        }

        @When("the user adds the first and second items to the cart")
        public void theUserAddsItemsToCart() {
            cartScreen.addFirstItemToCartFromDetailsPage();
            cartScreen.addSecondItemToCartFromDetailsPage();
            // Verify the cart contains 2 items
            int cartItemCount = cartScreen.getCartItemCount();
            Assert.assertEquals(cartItemCount, 2, "Cart item count should be 2 after adding two items.");
            System.out.println(cartItemCount);
        }

        @And("the user proceeds to checkout and enters address details")
        public void theUserProceedsToCheckout() {
            cartScreen.goToCart();
            Assert.assertTrue(checkoutScreen.isCheckoutPageDisplayed(), "User is in Checkout page.");
            checkoutScreen.startCheckout();
            Assert.assertTrue(checkoutScreen.isAddressPageDisplayed(), "User has entered his address details and is about to proceed to the checkout overview");
            String firstName = CommonUtils.getProperty("firstName");
            String lastName = CommonUtils.getProperty("lastName");
            String postalCode = CommonUtils.getProperty("postalCode");
            checkoutScreen.enterAddressDetails(firstName, lastName, postalCode);
            checkoutScreen.clickContinueButton();
            Assert.assertTrue(checkoutScreen.isCheckoutOverviewPageDisplayed(), "User has entered his address details and is about to proceed to the checkout overview");

        }

        @Then("the user should see the checkout overview and complete the purchase successfully")
        public void theUserShouldCompletePurchaseSuccessfully() {
            // Validate total price on the Checkout Overview screen
            String totalPrice = checkoutScreen.getTotalPrice();
            Assert.assertTrue(totalPrice.contains("43.18"), "Total price should match expected value.");
            // Complete the purchase
            checkoutScreen.completePurchase();
            // Validate confirmation message
            String confirmationMessage = checkoutScreen.getConfirmationMessage();
            Assert.assertEquals(confirmationMessage, "THANK YOU FOR YOU ORDER", "Confirmation message should match expected value.");
            // Log success and quit driver
            System.out.println("Order placed successfully: " + confirmationMessage);
        }
    }
