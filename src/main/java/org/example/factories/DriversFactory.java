package org.example.factories;


import org.example.utils.Helper;
import org.example.utils.IConstants;
import org.example.utils.ThreadLocalDriver;
import org.example.utils.ConfigFileReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriversFactory implements IConstants {

    public Properties Android = null;
    private WebDriver driver;
    private AndroidDriver<AndroidElement> androidDriver;
    private DesiredCapabilities caps;
    Process process;
    String androidVersion;
    String UDID;
    BufferedReader is;
    String line;
    String deviceName;

    public AndroidDriver<AndroidElement> getAndroidDriver() {
        if (androidDriver == null)
            androidDriver = createAndroidDriver();
        return androidDriver;
    }

    public void closeAndroidDriver() {
        ThreadLocalDriver.getTLDriver().quit();
    }

    public void setAndroidDriver(AndroidDriver<AndroidElement> androidDriver) {
        ThreadLocalDriver.setTLDriver(androidDriver);
    }

    private AndroidDriver<AndroidElement> createAndroidDriver() {

        UDID = getUDID();
        deviceName = getDeviceName();
        androidVersion = getAndroidVersion();

        caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.APPLICATION_NAME,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("applicationName"));
//        caps.setCapability(MobileCapabilityType.AUTO_WEBVIEW,
//                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("AUTO_WEBVIEW"));
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("platformName"));
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, androidVersion);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        caps.setCapability(MobileCapabilityType.UDID, UDID);
        caps.setCapability(MobileCapabilityType.FULL_RESET,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("fullReset"));
        caps.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("clearSystemFiles"));
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                AutomationName.ANDROID_UIAUTOMATOR2);
        caps.setCapability(MobileCapabilityType.NO_RESET, true);
        caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("autoGrantPermissions"));
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appPackage"));
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appActivity"));
        caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appWaitDuration"));
        try {
            if (FileReaderFactory.getInstance().getConfigReader(COMMON_PROPERTIES).getProperty("APK").equalsIgnoreCase("true")) {
                String apkPath = System.getProperty("user.dir") +
                        "/src/test/resources/apps/" + FileReaderFactory.getInstance().getConfigReader(COMMON_PROPERTIES).getProperty("ApkFileName");
                caps.setCapability(MobileCapabilityType.APP, new File(apkPath).getAbsolutePath());
            }

            ThreadLocalDriver.setTLDriver(new AndroidDriver<AndroidElement>(new URL(
                    FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appium_url")),
                    caps));
            ThreadLocalDriver.getTLDriver().manage().timeouts().implicitlyWait(
                    FileReaderFactory.getInstance().getConfigReader(COMMON_PROPERTIES).getImplicitlyWait(),
                    TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return ThreadLocalDriver.getTLDriver();
    }

    public AndroidDriver<AndroidElement> createNoRestAndroidDriver() {

        caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.APPLICATION_NAME,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("applicationName"));
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("platformName"));
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("platformVersion"));
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("deviceName"));
        caps.setCapability(MobileCapabilityType.UDID,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("UDID"));
        caps.setCapability(MobileCapabilityType.FULL_RESET,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("fullReset"));
        caps.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("clearSystemFiles"));
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME,
                AutomationName.ANDROID_UIAUTOMATOR2);
        caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("autoGrantPermissions"));
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appPackage"));
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appActivity"));
        caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION,
                FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appWaitDuration"));
        try {
            if (FileReaderFactory.getInstance().getConfigReader(COMMON_PROPERTIES).getProperty("APK").equalsIgnoreCase("true")) {
                String apkPath = System.getProperty("user.dir") +
                        "/src/test/resources/apps/" + FileReaderFactory.getInstance().getConfigReader(COMMON_PROPERTIES).getProperty("ApkFileName");
                caps.setCapability(MobileCapabilityType.APP, new File(apkPath).getAbsolutePath());
            }

            androidDriver = new AndroidDriver<AndroidElement>(new URL(FileReaderFactory.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appium_url")), caps);
            androidDriver.manage().timeouts().implicitlyWait(
                    FileReaderFactory.getInstance().getConfigReader(COMMON_PROPERTIES).getImplicitlyWait(),
                    TimeUnit.SECONDS);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return androidDriver;
    }


    public String getUDID() {

        try {
            process = Runtime.getRuntime().exec("adb devices");
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        try {
            while ((line = is.readLine()) != null) {
                if (Helper.hasDigits(line)) {
                    UDID = line.split("\t")[0];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return UDID;
    }

    public String getDeviceName() {
        try {
            process = Runtime.getRuntime().exec("adb devices -l");
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = new BufferedReader(new InputStreamReader(process.getInputStream()));

        try {

            while ((line = is.readLine()) != null) {
                if (line.contains(UDID)) {
                    if (line.contains("product")) {
                        deviceName = line.substring(line.lastIndexOf("device:") + 7);
                        deviceName = deviceName.split(" ")[0];
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deviceName;
    }

    public String getAndroidVersion() {
        try {
            process = Runtime.getRuntime().exec("adb -s " + UDID + " shell getprop ro.build.version.release");
        } catch (IOException e) {
            e.printStackTrace();
        }
        is = new BufferedReader(new InputStreamReader(process.getInputStream()));

        try {
            while ((line = is.readLine()) != null) {
                if (line != null) {
                    androidVersion = line;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return androidVersion;
    }

}
