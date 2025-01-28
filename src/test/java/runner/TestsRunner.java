/*
 * Copyright (c) Rakuten, Inc. All Rights Reserved.
 *
 * This program is the information assets which are handled as "Strictly Confidential".
 * Permission of Use is only admitted in Rakuten Inc.
 * Development Department.
 * If you don't have permission , MUST not be published, broadcast, rewritten for broadcast or publication
 * or redistributed directly or indirectly in any medium.
 *
 * Description - This file consists of the functionalities to run the feature files.
 * Created By - K S Pramod
 * Created on - 11-05-2020
 */
package runner;

//import com.Rakuten.rws.factories.FileReaderFactory;
//import com.Rakuten.rws.factories.TestRailConnect;
//import com.Rakuten.rws.utils.AppiumServer;
//import com.Rakuten.rws.utils.Helper;
//import com.Rakuten.rws.utils.IConstants;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.example.factories.FileReaderFactory;
import org.example.utils.AppiumServer;
import org.example.utils.Helper;
import org.example.utils.IConstants;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


/**
 * This class is used to select the scenarios from feature files for execution
 * tags = "not @PNPv2Integration"
 *
 * @author - K S Sushrava
 */
@CucumberOptions(features = {"Features/"},  glue = "steps", plugin = {
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "pretty",}, monochrome = true, publish = true)
public class TestsRunner extends AbstractTestNGCucumberTests {

    AppiumServer appiumServer;

    @BeforeClass

    public void beforeClass() {
        appiumServer = new AppiumServer();

            appiumServer.startAppiumServer();
            System.out.println("Started Appium server. ");
        }


    @AfterClass
    @Parameters({"Environment1"})
    public void afterClass() {

            //appiumServer.stopAppiumServer();
            System.out.println("Stopped Appium server. ");
        }
    }

