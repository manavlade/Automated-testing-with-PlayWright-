package com.playwright.playwright.playTest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.playwright.playwright.base.BaseTest;

public class FirstTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(FirstTest.class);

    @Test
    public void VerifyTitle(){
        page.navigate("https://www.youtube.com/watch?v=o_COPMFEa6Q&list=PLhW3qG5bs-L_kJz_Z0NnLmQHwLQ_vFFD2&index=2");
        logger.info("Title of the page is : " + page.title());

        if(page.isVisible("button:has-text('Accept all')")){
            page.click("button:has-text('Accept all')");
        }
        logger.info("Accept all button is clicked");
    }
    public static void main(String[] args) {
        try(Playwright playwright  = Playwright.create()){
            Browser browser =  playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate("https://www.youtube.com/watch?v=o_COPMFEa6Q&list=PLhW3qG5bs-L_kJz_Z0NnLmQHwLQ_vFFD2&index=2");
            logger.info("Title of the page is : " + page.title());
            page.close();
            browser.close();
        }
    }
}
