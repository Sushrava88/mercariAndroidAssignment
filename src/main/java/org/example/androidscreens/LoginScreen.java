package org.example.androidscreens;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.example.basePage.BaseScreen;
import org.openqa.selenium.support.FindBy;

public class LoginScreen extends BaseScreen {
    public LoginScreen(AndroidDriver<AndroidElement> androidDriver) {
        super(androidDriver);
    }

    @AndroidFindBy(accessibility = "test-Username")
    private AndroidElement usernameInput;

    @AndroidFindBy(accessibility = "test-Password")
    private AndroidElement passwordInput;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-LOGIN\"]/android.widget.TextView")
    private AndroidElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private AndroidElement productPageDisplayed;


    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public boolean isProductPageDisplayed() {
        return elementDisplayed(productPageDisplayed);
    }
}