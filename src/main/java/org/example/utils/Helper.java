package org.example.utils;

import org.example.factories.FileReaderFactory;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class Helper {

    private final static Random RANDOM = new SecureRandom();
    private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /*
     If the string is empty return false
     Find match between given string and regular expression using Pattern.matcher()
     Return if the string  has digits
     */
    public static boolean hasDigits(String value) {
        boolean flag = false;
        if (value.matches(".*\\d.*")) {
            flag = true;
        }
        return flag;
    }

    public static void print(String log) {
        ExtentCucumberAdapter.addTestStepLog("<span style=\"color: #ff0000;\"><string>Info:</strong></span>" + log);
    }

    public static String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

}
