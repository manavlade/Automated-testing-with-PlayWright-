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
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.playwright.utils.ConfigReader;
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

    String browserName = ConfigReader.get("browser");
    Boolean headless = ConfigReader.getBoolean("headless");
    int sloMo = ConfigReader.getInt("slowMo");
    String baseUrl = ConfigReader.get("BASE_URL");

    BrowserType browserType = getBrowserType(browserName);

    browser = browserType.launch(new LaunchOptions().setHeadless(headless).setSlowMo(sloMo));
    page = browser.newPage();
    page.navigate(baseUrl);
  }

  private BrowserType getBrowserType(String browserName) {
    switch (browserName.toLowerCase()) {
      case "firefox":
        return playwright.firefox();
      case "webkit":
        return playwright.webkit();
      case "chromium":
      default:
        return playwright.chromium();
    }
  }

  @AfterMethod
  public void TearDown(ITestResult result) {
    String screenShotPath;

    switch (result.getStatus()) {

      case ITestResult.SUCCESS:
        test.pass("Test passed");
        screenShotPath = new ScreenShotUtility().takeSuccessScreenShot(page, result.getName());
        test.addScreenCaptureFromBase64String(screenShotPath);
        break;

      case ITestResult.FAILURE:
        test.fail(result.getThrowable());
        screenShotPath = new ScreenShotUtility().takeFailureScreenShot(page, result.getName());
        test.addScreenCaptureFromBase64String(screenShotPath);
        break;

      case ITestResult.SKIP:
        test.skip("Test skipped");
        break;

      default:
        test.info("Test completed with unknown status");

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
