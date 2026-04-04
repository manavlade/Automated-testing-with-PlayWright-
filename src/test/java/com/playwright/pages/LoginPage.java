package com.playwright.pages;

import com.microsoft.playwright.Page;
import com.playwright.playwright.base.BaseTest;
import com.playwright.utils.LocatorReader;

public class LoginPage extends BaseTest{
    private final String userNameTextBoxLoginPage = LocatorReader.get("login.username.input");
    private final String passwordTextBoxLoginPage = LocatorReader.get("login.password.input");
    private final String loginButtonLoginPage = LocatorReader.get("login.submit.button");

    public LoginPage(Page page) {
        this.page = page;
    }


    public void login(String username, String password){
        page.fill(userNameTextBoxLoginPage, username);
        page.fill(passwordTextBoxLoginPage, password);
        page.click(loginButtonLoginPage);
    }
}
