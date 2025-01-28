package org.example.context;

import org.example.enums.EnvironmentType;
import org.example.factories.DriversFactory;
import org.example.factories.FileReaderFactory;
import org.example.pagegenerator.AndroidPage;
import org.example.utils.IConstants;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TestContext {
    private static EnvironmentType environmentType;
    private AndroidPage androidPage;
    private DriversFactory driversFactory;


    public TestContext() {
        driversFactory = new DriversFactory();

        environmentType = FileReaderFactory.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getEnvironment();

        switch (environmentType) {
            case ANDROID:
                androidPage = new AndroidPage(driversFactory.getAndroidDriver());
                break;
            case API:
                System.out.println("Running the API Tests. ");
                break;
            default:
                break;
        }
    }

    public DriversFactory getDriverFactory() {
        return driversFactory;
    }

    public AndroidPage getAndroidPage() {
        return androidPage;
    }

    public void setAndroidPage(AndroidDriver<AndroidElement> androidDriver){
        androidPage = new AndroidPage(androidDriver);
    }


    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

}
