package org.example.basePage;
import io.appium.java_client.MobileElement;
import org.example.factories.FileReaderFactory;
import org.example.utils.*;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.example.utils.Helper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class BaseScreen {
    protected AndroidDriver<AndroidElement> androidDriver;
    protected WebDriverWait wait;
    protected JavascriptExecutor jS;
    protected String value;
    protected boolean flag;
    protected MobileActions mobileActions;
    protected SeleniumUtil selUtil;
    protected Helper helper;
    private Logger logger = Logger.getLogger(BaseScreen.class.getName());


    public BaseScreen(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
        wait = new WebDriverWait(androidDriver, FileReaderFactory.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getExplicitWait());
        jS = androidDriver;
        mobileActions = new MobileActions(androidDriver);
    }

    public void waitForPageToLoad(MobileElement element, String pageName) {
        WebDriverWait wait = new WebDriverWait(androidDriver, 10);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("Transitioned to " + pageName + " successfully.");
        } catch (Exception e) {
            logger.severe("Failed to load " + pageName + ": " + e.getMessage());
            throw new RuntimeException("Page not loaded: " + pageName);
        }
    }

    protected void waitForElementToBeClickable(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(androidDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Log screen transitions for better debugging
    protected void logScreenTransition(String message) {
        System.out.println(message);
    }

    protected void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException exp) {
            print("Error: while waiting for " + (sec) + " seconds" + exp.toString());
        }
    }

    public void setTimeDuration(long timeUnitInSeconds) {
        if (FileReaderFactory.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getProperty("Environment").equalsIgnoreCase("android")) {
            androidDriver.manage().timeouts().implicitlyWait(timeUnitInSeconds, TimeUnit.SECONDS);
        }
    }

    protected boolean elementDisplayed(AndroidElement eleToCheck) {
        setTimeDuration(5);
        try {
            wait.until(ExpectedConditions.visibilityOf(eleToCheck));
            flag = true;
        } catch (TimeoutException exp) {
            flag = false;
        } catch (StaleElementReferenceException e) {
            flag = false;
        }
        setTimeDuration(FileReaderFactory.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getImplicitlyWait());
        return flag;
    }

    protected boolean elementSelected(AndroidElement element) {
        flag = "1".equals(element.getAttribute("value"));
        return flag;
    }

    protected boolean verifyTextOfAnElement(AndroidElement element, String textToVerify) {
        flag = false;
        try {
            flag = wait.until(ExpectedConditions.textToBePresentInElement(element, textToVerify));
        } catch (TimeoutException exp) {
            flag = false;
            System.out.printf("Exception: Expected text '-%s-' is not present in the element", textToVerify);
        }
        return flag;
    }


    protected boolean elementClickable(AndroidElement eleToCheck) {

        try {
            wait.until(ExpectedConditions.elementToBeClickable(eleToCheck));
            flag = true;
        } catch (TimeoutException exp) {
            flag = false;
            System.out.printf("%s is not visible", exp);
        }
        return flag;
    }
    protected boolean visibilityOfElementLocated(String locatorType, String locatorValue) {
        flag = false;
        try {
            switch (locatorType.toLowerCase()) {
                case "id":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    break;
                case "xpath":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    break;
                case "accessibility":
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + locatorType.toLowerCase());
            }
            flag = true;
        } catch (TimeoutException exp) {
            print("Waiting for element to display.");
        }
        return flag;
    }

    protected void acceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception exp) {
            System.out.println("Exception: Alert pop is not present. " + exp);
        }
    }


    protected void print(String strToPrint) {
        ExtentCucumberAdapter.addTestStepLog("<span style=\"color: #ff0000;\"><string>Info:</strong></span>" + strToPrint);
        Log.info(strToPrint);
    }

    public void startAndroidApp() {
        androidDriver.closeApp();
        wait(5);
    }

    public void clickOnAndroidGo() {
//        androidDriver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Go"));
        androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
}
