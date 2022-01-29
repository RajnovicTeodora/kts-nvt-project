package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.components.LogoutComponent;
import com.ftn.restaurant.e2e.pages.waiter.TableOptionsComponent;
import com.ftn.restaurant.e2e.pages.waiter.ViewAndPayOrderComponent;
import com.ftn.restaurant.e2e.pages.waiter.WaiterDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ViewAndPayOrderTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private LogoutComponent logoutComponent;
    private WaiterDashboardPage waiterDashboardPage;
    private TableOptionsComponent tableOptionsComponent;
    private ViewAndPayOrderComponent viewAndPayOrderComponent;

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
        viewAndPayOrderComponent = PageFactory.initElements(browser, ViewAndPayOrderComponent.class);
        logoutComponent = PageFactory.initElements(browser, LogoutComponent.class);
    }

    @Test
    public void payOrder(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));

        waiterDashboardPage.waitUntilTablePresent();

        //open table options for table 2
        waiterDashboardPage.areaTableButtonClicked(2);
        assertTrue(tableOptionsComponent.tableNumberTitlePresent("Table 2"));

        //6 active orders for table 2
        tableOptionsComponent.waitUntilOrderPresent();
        assertTrue(tableOptionsComponent.activeOrdersPresent(7));

        //view order 7
        tableOptionsComponent.viewActiveOrderButtonClicked(6);

        //number of active ordered items is 2 and ready items is 0
        viewAndPayOrderComponent.waitUntilOrderedItemPresent();
        assertTrue(viewAndPayOrderComponent.numberOfActiveOrderedItems(1));
        assertTrue(viewAndPayOrderComponent.containsItemsReadyToDeliver(0));
        assertTrue(viewAndPayOrderComponent.orderedItemPresent("Pizza", "ORDERED", 1, 25));

        //total cost is 25
        assertTrue(viewAndPayOrderComponent.totalCostPresent(25));

        //set received amount
        viewAndPayOrderComponent.setReceivedAmountInput(30);
        viewAndPayOrderComponent.changeLeftClick();
        assertTrue(viewAndPayOrderComponent.changeLeftPresent(5));

        //pay order
        viewAndPayOrderComponent.payOrderButtonClick();

        //number of orders is unchanged
        tableOptionsComponent.waitUntilOrderPresent();
        assertTrue(tableOptionsComponent.activeOrdersPresent(7));

        //view order 7
        viewAndPayOrderComponent.waitUntilToastTextNotPresent();
        tableOptionsComponent.viewActiveOrderButtonClicked(6);
        viewAndPayOrderComponent.waitUntilOrderedItemPresent();
        viewAndPayOrderComponent.waitUntilIsPaidPresent();

        //order is paid
        assertTrue(viewAndPayOrderComponent.orderIsPaid());
        assertTrue(viewAndPayOrderComponent.numberOfActiveOrderedItems(1));
        assertTrue(viewAndPayOrderComponent.containsItemsReadyToDeliver(0));
        assertTrue(viewAndPayOrderComponent.orderedItemPresent("Pizza", "ORDERED", 1, 25));

        //close payment module
        viewAndPayOrderComponent.closeViewAndPayOrderWindowClick();

        //close table options
        tableOptionsComponent.closeTableOptionsButtonClick();

        //logout
        waiterDashboardPage.logoutBtnClick();
        logoutComponent.confirmLogoutButtonClick();
    }

    @Test
    public void deliverReadyOrderedItems(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));

        waiterDashboardPage.waitUntilTablePresent();

        //open table options for table 2
        waiterDashboardPage.areaTableButtonClicked(2);
        assertTrue(tableOptionsComponent.tableNumberTitlePresent("Table 2"));

        //7 active orders for table 2
        tableOptionsComponent.waitUntilOrderPresent();
        assertTrue(tableOptionsComponent.activeOrdersPresent(7));

        //view order 2
        tableOptionsComponent.viewActiveOrderButtonClicked(2);

        //number of active ordered items is 1 and ready items is 1
        viewAndPayOrderComponent.waitUntilOrderedItemPresent();
        assertTrue(viewAndPayOrderComponent.numberOfActiveOrderedItems(1));
        assertTrue(viewAndPayOrderComponent.containsItemsReadyToDeliver(1));
        assertTrue(viewAndPayOrderComponent.orderedItemPresent("Pizza", "READY", 5, 25));

        //order is paid
        assertTrue(viewAndPayOrderComponent.orderIsPaid());

        //deliver ordered items with status ready
        viewAndPayOrderComponent.deliverReadyOrderedItems();

        //no active ordered items, order is finished
        viewAndPayOrderComponent.waitUntilToastTextNotPresent();
        assertTrue(viewAndPayOrderComponent.numberOfActiveOrderedItems(0));
        assertTrue(viewAndPayOrderComponent.orderedItemPresent("Pizza", "DELIVERED", 5, 25));

        //close payment
        viewAndPayOrderComponent.closeViewAndPayOrderWindowClick();
        tableOptionsComponent.waitUntilOrderPresent();

        //number of active orders for table 2 has been reduced by 1
        assertTrue(tableOptionsComponent.activeOrdersPresent(6));

        //close table options
        tableOptionsComponent.closeTableOptionsButtonClick();

        //logout
        waiterDashboardPage.logoutBtnClick();
        logoutComponent.confirmLogoutButtonClick();
    }


    @After
    public void closeSelenium() {
        browser.quit();
    }
}
