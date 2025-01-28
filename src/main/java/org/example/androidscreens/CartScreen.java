package org.example.androidscreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.example.basePage.BaseScreen;
import org.openqa.selenium.support.FindBy;


public class CartScreen extends BaseScreen {

    public CartScreen(AndroidDriver<AndroidElement> androidDriver) {
        super(androidDriver);
    }

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"test-Item\"])[1]/android.view.ViewGroup/android.widget.ImageView")
    private AndroidElement firstItemLink; // Link to first item's details page

    @AndroidFindBy(xpath = "(//android.view.ViewGroup[@content-desc=\"test-Item\"])[2]/android.view.ViewGroup/android.widget.ImageView")
    private AndroidElement secondItemLink; // Link to second item's details page

    @AndroidFindBy(accessibility = "test-ADD TO CART")
    private AndroidElement addToCartButton; // Add to cart button on the item screen

    @AndroidFindBy(accessibility = "test-BACK TO PRODUCTS")
    private AndroidElement backToShowcaseButton; // Back button to return to showcase screen

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView")
    private AndroidElement cartIcon;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='2']")
    private AndroidElement cartBadge; // Displays the number of items in the cart

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='YOUR CART']")
    private AndroidElement cartPageDisplayed;


    public void addFirstItemToCartFromDetailsPage() {
        firstItemLink.click(); // Navigate to first item's details page
        mobileActions.scrollToAnElementByText("ADD TO CART");
        waitForElementToBeClickable(addToCartButton);
        addToCartButton.click(); // Add to cart
         logScreenTransition("First Item Added to Cart");
        backToShowcaseButton.click(); // Return to showcase screen
        waitForElementToBeClickable(secondItemLink); // Wait for the second item to be visible
    }

    public void addSecondItemToCartFromDetailsPage() {
        secondItemLink.click(); // Navigate to second item's details page
        mobileActions.scrollToAnElementByText("ADD TO CART");
        waitForElementToBeClickable(addToCartButton);
        addToCartButton.click(); // Add to cart
        logScreenTransition("Second Item Added to Cart");
        backToShowcaseButton.click(); // Return to showcase screen
    }

    public int getCartItemCount() {
        String itemCountText = cartBadge.getText();
        return Integer.parseInt(itemCountText); // Convert the badge text to an integer
    }

    public void goToCart() {
        cartIcon.click();
        waitForPageToLoad(cartIcon, "Cart Page");
    }

    public boolean isCartPageDisplayed() {
        return elementDisplayed(cartPageDisplayed);
    }
}
