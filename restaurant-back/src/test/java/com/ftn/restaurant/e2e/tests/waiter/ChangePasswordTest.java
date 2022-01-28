package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.components.ChangePasswordComponent;
import com.ftn.restaurant.e2e.pages.components.LogoutComponent;
import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.waiter.WaiterDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class ChangePasswordTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private WaiterDashboardPage waiterDashboardPage;
    private LogoutComponent logoutComponent;
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
        logoutComponent = PageFactory.initElements(browser, LogoutComponent.class);
        changePasswordComponent = PageFactory.initElements(browser, ChangePasswordComponent.class);
    }

    @Test
    public void firstPasswordChangeTest(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));

        //clicked changed password option and entered new password "wer"
        waiterDashboardPage.changePasswordButtonClick();
        changePasswordComponent.setNewPasswordInput("wer");
        changePasswordComponent.regularPasswordChangeConfirmClick();

        //logout
        waiterDashboardPage.logoutBtnClick();

        //login with "wer" password
        logoutComponent.confirmLogoutButtonClick();
        loginPage.login("waiter", "wer");

        //successful login
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));
    }

    @Test
    public void regularPasswordChangeTest(){
        loginPage.login("Boban123", "test");
        assertTrue(waiterDashboardPage.urlPresent());

        //change password option is already open, enter new password "wer"
        changePasswordComponent.setNewPasswordInput("wer");
        changePasswordComponent.firstPasswordChangeConfirmClick();

        //logout
        waiterDashboardPage.logoutBtnClick();

        //login with "wer"
        logoutComponent.confirmLogoutButtonClick();
        loginPage.login("Boban123", "wer");

        //successful login
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("Boban123"));
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }

}
