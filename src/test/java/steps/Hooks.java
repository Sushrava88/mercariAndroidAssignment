package steps;


import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.example.androidscreens.LoginScreen;
import org.example.context.TestContext;
import org.example.enums.EnvironmentType;
import org.example.factories.FileReaderFactory;
import org.example.utils.IConstants;
import org.example.utils.Log;
import org.example.utils.SeleniumUtil;

import java.io.IOException;
import java.util.Collection;

public class Hooks {

    TestContext testContext;
    private EnvironmentType environmentType;
    private LoginScreen loginScreen;

    Collection<String> scenarioTags;
    Scenario scenario;

    public Hooks(TestContext context) {
        testContext = context;
        environmentType = FileReaderFactory.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES)
                .getEnvironment();
    }

//    @Before(order = 1)
//    public void startScenario(Scenario scenario) {
//        Log.startTestCase(scenario.getName());
//        ExtentCucumberAdapter.addTestStepLog("*********************************************************");
//        ExtentCucumberAdapter.addTestStepLog("Test execution started for Scenario: " + scenario.getName());
//        ExtentCucumberAdapter.addTestStepLog("*********************************************************");
//
//        this.scenario = scenario;
//        scenarioTags = scenario.getSourceTagNames();
//
//
//
//        for (String scenarioTag : scenarioTags) {
//            if (scenarioTag.contains("TermsAndConditions")) {
//
//                switch (environmentType) {
//                    case ANDROID:
//                        testContext.getDriverFactory().closeAndroidDriver();
//                        AndroidDriver<AndroidElement> driver = testContext.getDriverFactory().createNoRestAndroidDriver();
//                        testContext.setAndroidPage(driver);
//                        testContext.getDriverFactory().setAndroidDriver(driver);
//                        break;
//                    case IOS:
//                        System.out.println("creating no rest driver for IOS. ");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//
//        switch (environmentType) {
//            case ANDROID:
//                loginScreen = testContext.getAndroidPage().getLoginScreen();
//                break;
//            case API:
//                break;
//            default:
//                System.out.println(
//                        "No Page objects can be created due invalid environment type: "
//                                + environmentType);
//                break;
//        }
//    }

    @After(order = 1)
    public void endScenario(Scenario scenario) throws IOException {


        if (scenario.isFailed()) {
            System.out.printf("Scenario: %s is failed", scenario.getName());

            switch (environmentType) {
                case ANDROID:
                    scenario.attach(SeleniumUtil.getByteScreenshot(testContext.getDriverFactory().getAndroidDriver()), "image/png", scenario.getName());
                    break;
                case API:
                    System.out.println("Not taking the screenshot as we are testing API's");
                    break;
                default:
                    System.out.println(
                            "Unable to take the screenshot and attach to the HTML report due to invalid environment type: "
                                    + environmentType);
                    break;
            }
        }
        switch (environmentType) {
            case ANDROID:
                testContext.getDriverFactory().closeAndroidDriver();
                break;
            case API:
                System.out.println("...");
                break;
            default:
                break;
        }
        ExtentCucumberAdapter.addTestStepLog("*********************************************************");
        ExtentCucumberAdapter.addTestStepLog("Test execution completed for Scenario: " + scenario.getName());
        ExtentCucumberAdapter.addTestStepLog("*********************************************************");
        Log.endTestCase(scenario.getName());
    }


//    @AfterStep
//    public void takeScreenshot() {
//        switch (environmentType) {
//            case ANDROID:
//                scenario.attach(SeleniumUtil.getByteScreenshot(testContext.getDriverFactory().getAndroidDriver()), "image/png", scenario.getName());
//                break;
//
//            case API:
//                System.out.println("Not taking the screenshot as we are testing API's");
//                break;
//            default:
//                System.out.println(
//                        "Unable to take the screenshot and attach to the HTML report due to invalid environment type: "
//                                + environmentType);
//                break;
//        }
//    }
}
