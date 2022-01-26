package com.ftn.restaurant.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ftn.restaurant.e2e.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

public class ManagerReportTest {
    private WebDriver browser;

    private LoginPage loginPage;

    @Before
    public void setupSelenium() {
        // instantiate browser
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        browser = new ChromeDriver();
        // maximize window
        browser.manage().window().maximize();
        // navigate
        browser.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
    }

    @Test
    public void singInTest() {

        // set username
        loginPage.setUsernameInput("manager");
        loginPage.loginBtnClick();

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertEquals("http://localhost:4200/manager-dashboard", browser.getCurrentUrl());

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        //browser.quit();
    }

}
