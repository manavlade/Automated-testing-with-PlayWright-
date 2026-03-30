package com.playwright.utils;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import com.microsoft.playwright.Page;

public class ScreenShotUtility {

    public String takeScreenShotPage(Page page, String testName) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String screenShotPath = "test-output/Screenshots/" + testName + " " + timeStamp + ".png";

        byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenShotPath)).setFullPage(true));
        String base64 = Base64.getEncoder().encodeToString(screenshotBytes);
        return base64;
    }
}
