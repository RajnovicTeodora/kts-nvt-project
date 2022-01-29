package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.waiter.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class EditOrderTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private EditOrderPage editOrderPage;
    private WaiterDashboardPage waiterDashboardPage;
    private TableOptionsComponent tableOptionsComponent;
    private CustomizeOrderedItemComponent customizeOrderedItemComponent;
    private SearchMenuItemsPage searchMenuItemsPage;
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
        editOrderPage = PageFactory.initElements(browser, EditOrderPage.class);
        customizeOrderedItemComponent = PageFactory.initElements(browser, CustomizeOrderedItemComponent.class);
        searchMenuItemsPage = PageFactory.initElements(browser, SearchMenuItemsPage.class);
        viewAndPayOrderComponent = PageFactory.initElements(browser, ViewAndPayOrderComponent.class);
    }

    @Test
    public void test(){
        loginPage.login("waiter", "test");
        assertTrue(waiterDashboardPage.urlPresent());
        assertTrue(waiterDashboardPage.currentUsernamePresent("waiter"));

        waiterDashboardPage.waitUntilTablePresent();

        //open table options for table 2
        waiterDashboardPage.areaTableButtonClicked(2);
        assertTrue(tableOptionsComponent.tableNumberTitlePresent("Table 2"));

        //number of active orders is 7
        tableOptionsComponent.waitUntilOrderPresent();
        assertTrue(tableOptionsComponent.activeOrdersPresent(7));

        //edit order 8
        tableOptionsComponent.editActiveOrderButtonClicked(7);

        //edit order 8 window present
        editOrderPage.urlPresent(8);
        editOrderPage.waitUntilOrderedItemsPresent();

        //current order info
        assertTrue(editOrderPage.numberOfOrderedItemsPresent(2));
        assertTrue(editOrderPage.orderedItemPresent("Lasagna", "ORDERED", 1));
        assertTrue(editOrderPage.orderedItemPresent("Pizza", "ORDERED", 2));
        assertTrue(editOrderPage.totalCostPresent(65));
        editOrderPage.additionalNotesButtonClick();
        editOrderPage.waitUntilAdditionalNotesPresent();
        assertTrue(editOrderPage.additionalNotesContainsText("Old note"));

        //change note
        editOrderPage.setAdditionalNotesInput("New note");
        editOrderPage.confirmAdditionNotesButtonClick();
        editOrderPage.additionalNotesButtonClick();
        editOrderPage.waitUntilAdditionalNotesPresent();
        assertTrue(editOrderPage.additionalNotesContainsText("New note"));

        //delete ordered item pizza
        editOrderPage.closeAdditionNotesButtonClick();
        editOrderPage.deleteOrderedItemButtonClick(2);
        editOrderPage.confirmActionButtonClick();
        editOrderPage.waitUntilOrderedItemsPresent();

        //current order info
        assertTrue(editOrderPage.numberOfOrderedItemsPresent(1));
        assertTrue(editOrderPage.orderedItemPresent("Lasagna", "ORDERED", 1));
        assertTrue(editOrderPage.totalCostPresent(15));

        //edit ordered item lasagna
        editOrderPage.editOrderedItemButtonClick(1);
        customizeOrderedItemComponent.setQuantityInput(5);
        customizeOrderedItemComponent.saveChangesToCustomizedOrderedItemButtonClicked();
        editOrderPage.waitUntilOrderedItemsPresent();

        //current order info
        assertTrue(editOrderPage.numberOfOrderedItemsPresent(1));
        assertTrue(editOrderPage.orderedItemPresent("Lasagna", "PENDING", 5));
        assertTrue(editOrderPage.totalCostPresent(75));

        //add new ordered item
        //search  -> tea
        searchMenuItemsPage.insertSearchText("tea");
        searchMenuItemsPage.clickSearch();
        editOrderPage.waitUntilOldResultsNotPresent();
        editOrderPage.searchResultClick();
        assertTrue(customizeOrderedItemComponent.menuItemNamePresent("Tea"));
        assertTrue(customizeOrderedItemComponent.menuItemPricePresent(15));

        //set quantity to 3, set priority to 2
        customizeOrderedItemComponent.setQuantityInput(3);
        customizeOrderedItemComponent.prioritySelectClick();
        customizeOrderedItemComponent.selectPriorityOption(2);

        //add customized ordered item to order
        customizeOrderedItemComponent.addCustomizedOrderedItemButtonClicked();
        editOrderPage.waitUntilOrderedItemsPresent();

        //current order info
        assertTrue(editOrderPage.numberOfOrderedItemsPresent(2));
        assertTrue(editOrderPage.orderedItemPresent("Lasagna", "PENDING", 5));
        assertTrue(editOrderPage.orderedItemPresent("Tea", "PENDING", 3));
        assertTrue(editOrderPage.totalCostPresent(120));

        //save changes
        editOrderPage.confirmEditedOrderButtonClick();

        //open table options for table 2
        waiterDashboardPage.waitUntilTablePresent();
        waiterDashboardPage.areaTableButtonClicked(2);
        assertTrue(tableOptionsComponent.tableNumberTitlePresent("Table 2"));

        //number of active orders is 7
        tableOptionsComponent.waitUntilOrderPresent();
        assertTrue(tableOptionsComponent.activeOrdersPresent(7));

        //view order 8
        tableOptionsComponent.viewActiveOrderButtonClicked(7);

        //number of active ordered items is 2 and ready items is 0
        viewAndPayOrderComponent.waitUntilOrderedItemPresent();
        assertTrue(viewAndPayOrderComponent.numberOfActiveOrderedItems(2));
        assertTrue(viewAndPayOrderComponent.containsItemsReadyToDeliver(0));
        assertTrue(viewAndPayOrderComponent.totalCostPresent(120));
        assertTrue(viewAndPayOrderComponent.orderedItemPresent("Lasagna", "ORDERED", 5, 15));
        assertTrue(viewAndPayOrderComponent.orderedItemPresent("Tea", "ORDERED", 3, 15));

    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
