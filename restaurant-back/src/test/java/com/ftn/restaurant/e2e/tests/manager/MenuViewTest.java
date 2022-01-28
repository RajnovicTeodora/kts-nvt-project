package com.ftn.restaurant.e2e.tests.manager;

import com.ftn.restaurant.e2e.pages.*;
import com.ftn.restaurant.e2e.pages.manager.ManagerDashboardPage;
import com.ftn.restaurant.e2e.pages.manager.MenuViewPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MenuViewTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private ManagerDashboardPage managerDashboardPage;
    private MenuViewPage menuViewPage;
    private AddDrinkPage addDrinkPage;

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
        menuViewPage = PageFactory.initElements(browser, MenuViewPage.class);
        addDrinkPage = PageFactory.initElements(browser, AddDrinkPage.class);
    }

    @Test
    public void menuViewTest() {

        // set username
        loginPage.setUsernameInput("manager");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(managerDashboardPage.urlPresent());

        managerDashboardPage.menuViewBtnClick();

        //Add drink
        menuViewPage.addDrinkBtnClick();
        addDrinkPage.setNameInput("Test");
        addDrinkPage.setDrinkType();
        addDrinkPage.setDrinkContainer();
        addDrinkPage.setFileInput("C:\\Users\\rajno\\Desktop\\3.jpg");
        addDrinkPage.submitDrinkBtnClick();

        //Test search for added drink
        menuViewPage.setMenuItemSearchInput("Test");
        menuViewPage.submitBtnClick();

        //Wait till items are showing
        menuViewPage.waitUntilItemsPresent();

        // Validate search
        assertTrue(menuViewPage.menuItemsTitleTextsSizeCompare(1));
        assertTrue(menuViewPage.menuItemsContainText("Test"));

        // Change price
        // Button should be disabled
        assertFalse(menuViewPage.isSetOnMenuEnabled(0));

        menuViewPage.setMenuItemPrice("22");
        menuViewPage.setMenuItemPurchasePrice("12");
        menuViewPage.savePricesBtnClick();

        menuViewPage.waitUntilItemsPresent();
        // Button should be disabled because the price is valid from tomorrow
        assertTrue(menuViewPage.isSetOnMenuEnabled(0));

        // Set as active menu item
        menuViewPage.setOnMenuBtnClick(0);

        // Delete item
        menuViewPage.deleteBtnClick(0);
        menuViewPage.waitUntilItemsPresent();

        assertTrue(menuViewPage.menuItemsTitleTextsSizeCompare(0));
        assertFalse(menuViewPage.menuItemsContainText("Test"));
        // Search and confirm no results found for deleted item

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
        //browser.quit();
    }

}
