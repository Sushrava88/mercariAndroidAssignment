package org.example.pagegenerator;

import org.example.androidscreens.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.example.factories.PagesFactory;

public class AndroidPage extends PagesFactory {

    public AndroidPage(AndroidDriver<AndroidElement> androidDriver) {
        super(androidDriver);
    }


    private LoginScreen loginScreen;
    private CartScreen cartScreen;
    private CheckoutScreen checkoutScreen;


    public LoginScreen getLoginScreen() {
        return (loginScreen == null) ? loginScreen = new LoginScreen(this.androidDriver) : loginScreen;
    }

    public CartScreen getCartScreen() {
        return (cartScreen == null) ? cartScreen = new CartScreen(this.androidDriver) : cartScreen;
    }

    public CheckoutScreen getCheckoutScreen() {
        return (checkoutScreen == null) ? checkoutScreen = new CheckoutScreen(this.androidDriver) : checkoutScreen;
    }
}
