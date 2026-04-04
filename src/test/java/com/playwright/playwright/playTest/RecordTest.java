package com.playwright.playwright.playTest;

import org.testng.annotations.Test;

import com.playwright.pages.LoginPage;
import com.playwright.playwright.base.BaseTest;
import com.playwright.utils.ConfigReader;

public class RecordTest extends BaseTest {
    @Test
    void test() {
        String baseUrl = ConfigReader.get("BASE_URL");
        String username = ConfigReader.get("defaultUsername");
        String password = ConfigReader.get("defaultPassword");

        LoginPage loginpage = new LoginPage(page);

        page.navigate(baseUrl);
        test.info("Navigated to login page: " + baseUrl);

        loginpage.login(username, password);
        test.info("Attempted login with username: " + username);

        test.info("Login test completed successfully");
    }
}