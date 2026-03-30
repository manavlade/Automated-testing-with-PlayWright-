package com.playwright.pages;

import com.microsoft.playwright.Page;
import com.playwright.playwright.base.BaseTest;

public class LoginTest extends  BaseTest{
    private Page page;
    private final String userNameTextBoxLoginPage = "input[name='username']";
    private final String passwordTextBoxLoginPage = "input[name='password']";
    private final String LoginButtonLoginPage = "button[id='submit']";

    public LoginTest(Page page) {
        this.page = page;
    }

    public void login(String username, String password){
        page.fill(userNameTextBoxLoginPage, username);
        page.fill(passwordTextBoxLoginPage, password);
        page.click(LoginButtonLoginPage);

    }
}
