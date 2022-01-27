package com.ftn.restaurant.e2e.tests;

import com.ftn.restaurant.e2e.pages.ItemViewPage;
import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.ManagerDashboardPage;
import com.ftn.restaurant.e2e.pages.MenuViewPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ItemViewTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private ManagerDashboardPage managerDashboardPage;
    private ItemViewPage itemViewPage;
    private MenuViewPage menuViewPage;

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
        itemViewPage = PageFactory.initElements(browser, ItemViewPage.class);
        menuViewPage = PageFactory.initElements(browser, MenuViewPage.class);
    }

    @Test
    public void itemViewTest() {

        // set username
        loginPage.setUsernameInput("manager");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(managerDashboardPage.urlPresent());

        //Test search
        itemViewPage.setItemSearchInput("c");
        itemViewPage.submitBtnClick();

        //Wait till items are showing
        itemViewPage.waitUntilItemsPresent();

        // Validate search
        assertTrue(itemViewPage.itemsTitleTextsSizeCompare(2));
        assertTrue(itemViewPage.itemsTitleTextsContainText("c"));

        // Approve first item
        itemViewPage.approveBtnClick(0);

        // Check if fist item is moved to menu
        managerDashboardPage.menuViewBtnClick();
        menuViewPage.titleInMenuItemTitleTexts("Caesar salad");

        // Back to items view
        managerDashboardPage.itemsViewBtnClick();
        itemViewPage.waitUntilItemsPresent();
        // Delete item
        assertTrue(itemViewPage.itemsTitleTextsSizeCompare(2));
        itemViewPage.deleteBtnClick(0);
        itemViewPage.waitUntilItemsPresent();
        assertTrue(itemViewPage.itemsTitleTextsSizeCompare(1));

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
