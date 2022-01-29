package com.ftn.restaurant.e2e.tests.manager;

import com.ftn.restaurant.e2e.pages.manager.EditPaycheckPage;
import com.ftn.restaurant.e2e.pages.manager.ManagerDashboardPage;
import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.shared.PaychecksPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaychecksTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private ManagerDashboardPage managerDashboardPage;
    private PaychecksPage paychecksPage;
    private EditPaycheckPage editPaycheckPage;

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
        managerDashboardPage = PageFactory.initElements(browser, ManagerDashboardPage.class);
        paychecksPage = PageFactory.initElements(browser, PaychecksPage.class);
        editPaycheckPage = PageFactory.initElements(browser, EditPaycheckPage.class);
    }

    @Test
    public void paychecksTest() {

        // set username
        loginPage.setUsernameInput("manager");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(managerDashboardPage.urlPresent());
        managerDashboardPage.paychecksViewBtnClick();

        paychecksPage.waitUntilTablePresent();

        //Search and filter
        paychecksPage.setEmployeeSearchInput("misko");
        paychecksPage.setRoleFilter();
        paychecksPage.submitBtnClick();

        paychecksPage.waitUntilTablePresent();
        paychecksPage.waitUntilTablePresent();

        assertTrue(paychecksPage.tableRowsSizeCompare(1));
        assertTrue(paychecksPage.usernamesContainText("misko"));
        assertTrue(paychecksPage.rolesContainText("Bartender"));

        //Edit found user's paycheck
        paychecksPage.editPaycheckBtnClick(0);
        
        paychecksPage.waitUntilTablePresent();
        editPaycheckPage.setPaycheckInput("19");
        editPaycheckPage.submitBtnClick();
        
        paychecksPage.invisibilityWait();
        paychecksPage.waitUntilTablePresent();

        assertTrue(paychecksPage.comparePaychecks("19", 0));

        // Log out
        managerDashboardPage.menuViewBtnClick();
        managerDashboardPage.logoutBtnClick();
        managerDashboardPage.confirmLogoutBtnClick();
        loginPage.urlPresent();
        assertEquals("http://localhost:4200/login", browser.getCurrentUrl());
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        browser.quit();
    }
}
