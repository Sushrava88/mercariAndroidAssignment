package org.example.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

import java.io.File;
import java.util.HashMap;

public class AppiumServer {

    private AppiumDriverLocalService server;

    public void startAppiumServer() {

        AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder();
        // To get the available port, if APPIUM Default 4723 is used by another process
        serviceBuilder.withIPAddress("0.0.0.0");
        serviceBuilder.usingPort(4723);
        serviceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, "4723");
        serviceBuilder.withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, "4723");

        // Tell where node is installed.
        serviceBuilder.usingDriverExecutable(new File("/usr/local/bin/node"));
        // Tell where APPIUM is installed.
        serviceBuilder.withAppiumJS(new File("/usr/local/bin/appium"));
        // The XCUITest driver requires that a path to the CARTHAGE binary is in the
        // PATH variable. So setting the path
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH", "/usr/local/bin:" + System.getenv("PATH"));
        serviceBuilder.withEnvironment(environment);

        server = AppiumDriverLocalService.buildService(serviceBuilder);
        server.start();

        if (server.isRunning()) {
            System.out.println("Appium Server Started");
        } else {
            System.out.println("Server startup failed");
            System.exit(0);
        }
    }

    public void stopAppiumServer() {
        if (server.isRunning()) {
            server.stop();
        } else {
            System.out.println("Server stop failed");
            System.exit(0);
        }
    }

}
