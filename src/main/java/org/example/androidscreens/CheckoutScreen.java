package org.example.androidscreens;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.java.en.And;
import org.example.basePage.BaseScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutScreen extends BaseScreen {

    public CheckoutScreen(AndroidDriver<AndroidElement> androidDriver) {super(androidDriver);}

    @AndroidFindBy(accessibility = "test-CHECKOUT")
    private AndroidElement checkoutButton;

    @AndroidFindBy(accessibility = "test-First Name")
    private AndroidElement firstNameInput; // Input field for First Name

    @AndroidFindBy(accessibility = "test-Last Name")
    private AndroidElement lastNameInput; // Input field for Last Name

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    private AndroidElement postalCodeInput; // Input field for Postal Code

    @AndroidFindBy(accessibility = "test-CONTINUE")
    private AndroidElement continueButton; // Button to navigate to the overview page

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Total: $43.18']")
    private AndroidElement totalPriceLabel; // Total price label on the overview page

    @AndroidFindBy(accessibility = "test-FINISH")
    private AndroidElement finishButton; // Button to complete the purchase

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='THANK YOU FOR YOU ORDER']")
    private AndroidElement confirmationMessage;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='YOUR CART']")
    private AndroidElement checkoutPageDisplayed;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='CHECKOUT: OVERVIEW']")
    private AndroidElement checkoutOverviewPageDisplayed;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='CHECKOUT: INFORMATION']")
    private AndroidElement addressPageDisplayed;


    public void startCheckout() {
        mobileActions.scrollToAnElementByText("CHECKOUT");
        checkoutButton.click();
    }

    public boolean isCheckoutPageDisplayed() {
        return elementDisplayed(checkoutPageDisplayed);
    }

    public boolean isCheckoutOverviewPageDisplayed() {
        return elementDisplayed(checkoutOverviewPageDisplayed);
    }

    public boolean isAddressPageDisplayed() {
        return elementDisplayed(addressPageDisplayed);
    }

    public void enterAddressDetails(String firstName, String lastName, String postalCode) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        postalCodeInput.sendKeys(postalCode);
        logScreenTransition("Address Input Screen");
    }

    public void clickContinueButton() {
        continueButton.click();
        mobileActions.scrollToAnElementByText("FINISH");
        waitForPageToLoad(totalPriceLabel, "Checkout Overview Screen");
    }

    public String getTotalPrice() {
        return totalPriceLabel.getText();
    }

    public void completePurchase() {
        finishButton.click();
       waitForPageToLoad(confirmationMessage, "Confirm Purchase Screen");
    }

    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }
}