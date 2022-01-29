package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.waiter.TableOptionsComponent;
import com.ftn.restaurant.e2e.pages.waiter.WaiterDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class TableOptionsTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private WaiterDashboardPage waiterDashboardPage;
    private TableOptionsComponent tableOptionsComponent;

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
        tableOptionsComponent = PageFactory.initElements(browser, TableOptionsComponent.class);
    }

    @Test
    public void test(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));

        waiterDashboardPage.waitUntilTablePresent();

        //open table options for table 3
        waiterDashboardPage.areaTableButtonClicked(3);
        assertTrue(tableOptionsComponent.tableNumberTitlePresent("Table 3"));

        //table is unclaimed and unoccupied
        assertTrue(tableOptionsComponent.tableClaimedTextPresent("Table is unclaimed"));
        assertTrue(tableOptionsComponent.tableOccupiedTextPresent("Table is unoccupied"));

        //change to claimed
        tableOptionsComponent.tableClaimedStatusButtonClick();
        assertTrue(tableOptionsComponent.tableClaimedTextPresent("Table is claimed"));

        //change to occupied
        tableOptionsComponent.tableOccupiedStatusButtonClick();
        assertTrue(tableOptionsComponent.tableOccupiedTextPresent("Table is occupied"));

        //active orders list present and has 0 orders
        assertTrue(tableOptionsComponent.activeOrdersTitlePresent());
        assertTrue(tableOptionsComponent.activeOrdersPresent(0));

        //cant leave (unclaim) table while its occupied
        tableOptionsComponent.tableClaimedStatusButtonClick();
        waiterDashboardPage.toastTextPresent("Can't leave table while its occupied");
        assertTrue(tableOptionsComponent.tableClaimedTextPresent("Table is claimed"));

        //change to unoccupied
        tableOptionsComponent.tableOccupiedStatusButtonClick();
        assertTrue(tableOptionsComponent.tableOccupiedTextPresent("Table is unoccupied"));

        //leave table
        tableOptionsComponent.tableClaimedStatusButtonClick();
        assertTrue(tableOptionsComponent.tableClaimedTextPresent("Table is unclaimed"));
    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
