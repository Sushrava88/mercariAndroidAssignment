package org.example.factories;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebDriver;

public class PagesFactory {
	protected WebDriver driver;
	protected AndroidDriver<AndroidElement> androidDriver;
	protected IOSDriver<IOSElement> iosDriver;

	public PagesFactory(WebDriver driver) {
		this.driver = driver;
	}

	public PagesFactory(AndroidDriver<AndroidElement> androidDriver) {
		this.androidDriver = androidDriver;
	}

	public PagesFactory(IOSDriver<IOSElement> iosDriver) {
		this.iosDriver = iosDriver;
	}
}
