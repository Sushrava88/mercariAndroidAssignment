package org.example.utils;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class MobileActions {

    @SuppressWarnings("unused")
    private AndroidDriver<AndroidElement> androidDriver;

    public MobileActions(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    public AndroidElement scrollToAnElementByText(String text) {
        return androidDriver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())" +
                ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
    }

}
