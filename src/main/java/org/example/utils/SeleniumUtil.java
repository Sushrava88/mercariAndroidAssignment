package org.example.utils;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class SeleniumUtil {

    public static void saveScreenshot(String name, AndroidDriver<AndroidElement> androidDriver) {
        String imagePath = IConstants.SCREENSHOT;
        String Base64StringOfScreenshot = "";

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmss");
        String sDate = sdf.format(date);

        File src = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);

        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);

        attachScreenshotToExtent(Base64StringOfScreenshot);
    }

    public static void saveScreenshot(String name, IOSDriver<IOSElement> iosDriver) {
        String imagePath = IConstants.SCREENSHOT;
        String Base64StringOfScreenshot = "";

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmss");
        String sDate = sdf.format(date);

        File src = ((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(imagePath + "image_" + sDate + ".png"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        byte[] fileContent = new byte[10000];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);

        attachScreenshotToExtent(Base64StringOfScreenshot);
    }

    public static void saveScreenshot(String name, WebDriver driver) {
        String imagePath = IConstants.SCREENSHOT;
        String Base64StringOfScreenshot = "";

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmss");
        String sDate = sdf.format(date);

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File(imagePath + "image_" + sDate + ".png"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        byte[] fileContent = new byte[10000];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);

        attachScreenshotToExtent(Base64StringOfScreenshot);
    }

    public static void attachScreenshotToExtent(String imagePath) {
        try {
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(imagePath);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static byte[] getByteScreenshot(AndroidDriver<AndroidElement> androidDriver) {
        File src = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public static byte[] getByteScreenshot(IOSDriver<IOSElement> iosDriver) {
        File src = ((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public static byte[] getByteScreenshot(WebDriver driver) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(src);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public static void attachExistingScreenShot(String imagePath) {
        try {
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBrowserName(WebDriver driver) {
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        return caps.getBrowserName();
    }

    public static void scrollDown(IOSDriver<IOSElement> iosDriver) {
        HashMap<String, String> scrollObject = new HashMap<>();
        JavascriptExecutor js = iosDriver;
        scrollObject.put("direction", "down");
        js.executeScript("mobile: scroll", scrollObject);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void elementScreenshot(IOSDriver<IOSElement> iosDriver, IOSElement element) {

        File screenshotLocation = null;
        try {
            File scrFile = ((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.FILE);

            BufferedImage fullImg = ImageIO.read(scrFile);
            int x = element.getLocation().getX();
            int y = element.getLocation().getY();
            int eleWidth = element.getSize().getWidth();
            int eleHeight = element.getSize().getHeight();

            BufferedImage eleScreenshot = fullImg.getSubimage(x, y, eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", scrFile);

            String path = IConstants.SCREENSHOT + UUID.randomUUID() + "" + ".png";

            screenshotLocation = new File(System.getProperty("user.dir") + "/" + path);
            FileUtils.copyFile(scrFile, screenshotLocation);

            File file = new File(path);
            BufferedImage bufferedImage = ImageIO.read(file);

            byte[] image = null;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                ImageIO.write(bufferedImage, "png", bos);
                image = bos.toByteArray();
            } catch (Exception e) {
            }

            //Allure.addAttachment(element.getText() + "_" + UUID.randomUUID(), new ByteArrayInputStream(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
