package org.example.utils;

import org.example.enums.EnvironmentType;
import org.example.factories.FileReaderFactory;
import com.aventstack.extentreports.service.ExtentService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;

    public ConfigFileReader(String propertyFilePath) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException exception) {
            System.out.println(
                    "Configuration.properties not found at: " + propertyFilePath + "\n" + exception.toString());
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getProperty(String propertyName) {
        String propertyValue = properties.getProperty(propertyName);
        if (propertyValue != null)
            return propertyValue;
        else
            throw new RuntimeException("Property name specified in the properties file for the Key:" + propertyName);
    }

    public long getImplicitlyWait() {
        String implicitlyWait = properties.getProperty("ImplicitWait");
        try {
            return Long.parseLong(implicitlyWait);
        } catch (NumberFormatException e) {
            System.out.println("Not able to parse value : : " + implicitlyWait + " in to Long");
            throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
        }
    }

    public long getExplicitWait() {
        String implicitlyWait = properties.getProperty("ExplicitWait");
        try {
            return Long.parseLong(implicitlyWait);
        } catch (NumberFormatException e) {
            System.out.println("Not able to parse value : : " + implicitlyWait + " in to Long");
            throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
        }
    }

    public EnvironmentType getEnvironment() {
        String environmentName = properties.getProperty("Environment");
        if (environmentName.equalsIgnoreCase("android"))
            return EnvironmentType.ANDROID;
        else
            throw new RuntimeException(
                    "Environment Type Key value in Configuration.properties is not matched : " + environmentName);
    }
}