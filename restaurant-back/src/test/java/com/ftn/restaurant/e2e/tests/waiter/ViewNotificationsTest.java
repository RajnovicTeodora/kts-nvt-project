package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.components.ChangePasswordComponent;
import com.ftn.restaurant.e2e.pages.components.LogoutComponent;
import com.ftn.restaurant.e2e.pages.components.ViewNotificationsComponent;
import com.ftn.restaurant.e2e.pages.waiter.WaiterDashboardPage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class ViewNotificationsTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private WaiterDashboardPage waiterDashboardPage;
    private ViewNotificationsComponent viewNotificationsComponent;

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
        viewNotificationsComponent = PageFactory.initElements(browser, ViewNotificationsComponent.class);
    }

    @Test
    public void test(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));

        waiterDashboardPage.notificationsButtonClick();
        viewNotificationsComponent.oldNotifTabClicked();
        assertTrue(viewNotificationsComponent.oldNotifContentSize(1));

        viewNotificationsComponent.newNotifTabClicked();
        assertTrue(viewNotificationsComponent.newNotifContentSize(5));

        viewNotificationsComponent.setViewedNotificationButtonClicked();

        viewNotificationsComponent.oldNotifTabClicked();
        assertTrue(viewNotificationsComponent.oldNotifContentSize(2));

        viewNotificationsComponent.newNotifTabClicked();
        assertTrue(viewNotificationsComponent.newNotifContentSize(4));
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
