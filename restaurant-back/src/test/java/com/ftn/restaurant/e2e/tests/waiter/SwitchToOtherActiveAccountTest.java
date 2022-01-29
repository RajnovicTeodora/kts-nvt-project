package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.components.ChangePasswordComponent;
import com.ftn.restaurant.e2e.pages.components.OtherAccountsComponent;
import com.ftn.restaurant.e2e.pages.components.ViewNotificationsComponent;
import com.ftn.restaurant.e2e.pages.waiter.WaiterDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class SwitchToOtherActiveAccountTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private WaiterDashboardPage waiterDashboardPage;
    private OtherAccountsComponent otherAccountsComponent;
    private ChangePasswordComponent changePasswordComponent;

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
        waiterDashboardPage = PageFactory.initElements(browser, WaiterDashboardPage.class);
        otherAccountsComponent = PageFactory.initElements(browser, OtherAccountsComponent.class);
        changePasswordComponent = PageFactory.initElements(browser, ChangePasswordComponent.class);
    }

    @Test
    public void test(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        waiterDashboardPage.waitUntilActiveAccountPresent();

        //current account is "waiter" and number of active accounts is 1
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));
        assertTrue(waiterDashboardPage.activeAccountsSize(1));

        //login to another account
        waiterDashboardPage.otherAccountButtonClick();
        otherAccountsComponent.otherAccountLoginBtnClick();
        loginPage.setUsernameInput("otherWaiter");
        loginPage.setPasswordInput("test");
        loginPage.loginBtnForOtherAccountsClick();
        waiterDashboardPage.waitUntilActiveAccountPresent();

        //current account is "otherWaiter" and number of active accounts is 2
        assertTrue(waiterDashboardPage.currentUsernamePresent("otherWaiter"));
        assertTrue(waiterDashboardPage.activeAccountsSize(2));

        //switch to active account "waiter"
        waiterDashboardPage.switchToActiveAccount();

        //current account is "waiter" and number of active accounts is 1
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));
        assertTrue(waiterDashboardPage.activeAccountsSize(2));
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
