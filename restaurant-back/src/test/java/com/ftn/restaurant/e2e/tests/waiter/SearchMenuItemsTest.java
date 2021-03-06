package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.Utilities;
import com.ftn.restaurant.e2e.pages.components.LogoutComponent;
import com.ftn.restaurant.e2e.pages.waiter.EditOrderPage;
import com.ftn.restaurant.e2e.pages.waiter.SearchMenuItemsPage;
import com.ftn.restaurant.e2e.pages.waiter.WaiterDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class SearchMenuItemsTest {

    private WebDriver browser;
    private LoginPage loginPage;
    private SearchMenuItemsPage searchMenuItemsPage;
    private WaiterDashboardPage waiterDashboardPage;
    private TableOptionsTest tableOptionsTest;
    private LogoutComponent logoutComponent;
    private EditOrderPage editOrderPage;

    @Before
    public void setupSelenium() {
        // instantiate browser
        /*System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\firefoxdriver.exe");
        driver = new FirefoxDriver();*/
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        browser = new ChromeDriver();
        // maximize window
        browser.manage().window().maximize();
        // navigate
        browser.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        waiterDashboardPage = PageFactory.initElements(browser, WaiterDashboardPage.class);
        searchMenuItemsPage = PageFactory.initElements(browser, SearchMenuItemsPage.class);
        tableOptionsTest = PageFactory.initElements(browser, TableOptionsTest.class);
        logoutComponent = PageFactory.initElements(browser, LogoutComponent.class);
        editOrderPage = PageFactory.initElements(browser, EditOrderPage.class);
    }

    @Test
    public void searchMenuItemsTest(){
        loginPage.login("waiter", "test");
        assertTrue(Utilities.urlWait(browser, "http://localhost:4200/waiter-dashboard",10));
        browser.navigate().refresh();
        waiterDashboardPage.waitUntilTablePresent();
        waiterDashboardPage.areaTableButtonClicked(2);
        searchMenuItemsPage.clickNewOrder();
        searchMenuItemsPage.insertSearchText("sp");
        searchMenuItemsPage.clickSearch();
        editOrderPage.waitUntilOldResultsNotPresent();
        assertTrue(searchMenuItemsPage.confirmSearchSuccess("sp"));
    }

    @Test
    public void drinkTypesTest(){
        loginPage.login("waiter", "test");
        assertTrue(Utilities.urlWait(browser, "http://localhost:4200/waiter-dashboard",10));
        browser.navigate().refresh();
        waiterDashboardPage.waitUntilTablePresent();
        waiterDashboardPage.areaTableButtonClicked(2);
        searchMenuItemsPage.clickNewOrder();
        searchMenuItemsPage.drinkClick();
        assertTrue(searchMenuItemsPage.confirmDrinkCategories());
    }

    @Test
    public void dishesTest(){
        loginPage.login("waiter", "test");
        assertTrue(Utilities.urlWait(browser, "http://localhost:4200/waiter-dashboard",10));
        browser.navigate().refresh();
        waiterDashboardPage.waitUntilTablePresent();
        waiterDashboardPage.areaTableButtonClicked(2);
        searchMenuItemsPage.clickNewOrder();
        searchMenuItemsPage.dishClick();
        searchMenuItemsPage.desertClick();
        editOrderPage.waitUntilOldResultsNotPresent();
        assertTrue(searchMenuItemsPage.confirmDishes());
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
