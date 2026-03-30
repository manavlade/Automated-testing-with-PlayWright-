package com.playwright.playwright.base;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.playwright.utils.ExtentManager;
import com.playwright.utils.ScreenShotUtility;

public class BaseTest {
  protected Playwright playwright;
  protected Browser browser;
  protected Page page;
  protected ExtentReports extent;
  protected ExtentTest test;

  @BeforeMethod
  public void setup(Method method) {
    extent = ExtentManager.getInstance();
    test = extent.createTest(method.getName());
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
    page = browser.newPage();
  }

  @AfterMethod
  public void TearDown(ITestResult result) {

    if (result.getStatus() == ITestResult.FAILURE) {
      test.fail(result.getThrowable());

      String screenShotPath = new ScreenShotUtility().takeScreenShotPage(page, result.getName());
      test.addScreenCaptureFromBase64String(screenShotPath);

    } else if (result.getStatus() == ITestResult.SUCCESS) {
      test.pass("Test passed");
      String screenShotPath = new ScreenShotUtility().takeScreenShotPage(page, result.getName());
      test.addScreenCaptureFromBase64String(screenShotPath);

    } else if (result.getStatus() == ITestResult.SKIP) {
      test.skip("Test skipped");
    }
    extent.flush();

    if (page != null) {
      page.close();
    }
    if (browser != null) {
      browser.close();
    }
    if (playwright != null) {
      playwright.close();
    }
  }
}
