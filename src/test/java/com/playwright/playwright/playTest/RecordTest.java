package com.playwright.playwright.playTest;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;
import com.playwright.pages.LoginTest;
import com.playwright.playwright.base.BaseTest;

public class RecordTest extends  BaseTest {
    @Test
    void test() {

        LoginTest logintest = new LoginTest(page);
        page.navigate("https://practicetestautomation.com/practice-test-login/");
        test.info("Navigated to login page");
        logintest.login("student", "Password123");
        test.info("Cleared all test data");
        
        // page.navigate("https://demo.playwright.dev/todomvc/#/");
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?")).click();
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?")).click();
        // page.locator("html").click(new Locator.ClickOptions()
        //         .setButton(MouseButton.RIGHT));
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?")).click();
        // page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos")).click();
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?")).click();
        // page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos")).click();
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?")).click();
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?"))
        //         .fill("search history");
        // page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("What needs to be done?")).press("Enter");
        // page.getByTestId("todo-title").click();
        // page.getByText("This is just a demo of TodoMVC for testing, not the real TodoMVC app. todosMark").click();


    }
}