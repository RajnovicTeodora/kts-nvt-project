package com.ftn.restaurant.e2e.tests.waiter;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.waiter.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class CreateOrderTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private WaiterDashboardPage waiterDashboardPage;
    private TableOptionsComponent tableOptionsComponent;
    private CreateOrderPage createOrderPage;
    private CustomizeOrderedItemComponent customizeOrderedItemComponent;
    private SearchMenuItemsPage searchMenuItemsPage;
    private ViewAndPayOrderComponent viewAndPayOrderComponent;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        browser = new ChromeDriver();

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        waiterDashboardPage = PageFactory.initElements(browser, WaiterDashboardPage.class);
        tableOptionsComponent = PageFactory.initElements(browser, TableOptionsComponent.class);
        createOrderPage = PageFactory.initElements(browser, CreateOrderPage.class);
        customizeOrderedItemComponent = PageFactory.initElements(browser, CustomizeOrderedItemComponent.class);
        searchMenuItemsPage = PageFactory.initElements(browser, SearchMenuItemsPage.class);
        viewAndPayOrderComponent = PageFactory.initElements(browser, ViewAndPayOrderComponent.class);

        browser.manage().window().maximize();
        // navigate
        browser.navigate().to("http://localhost:4200/");
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

        //create new order selected
        tableOptionsComponent.createOrderButtonClick();

        //create order for table number 2 window present
        createOrderPage.urlPresent(2);

        //no ordered items, total cost is 0
        assertTrue(createOrderPage.totalCostPresent(0));

        //search  -> Pizza
        searchMenuItemsPage.insertSearchText("pizza");
        searchMenuItemsPage.clickSearch();
        createOrderPage.waitUntilOldResultsNotPresent();
        createOrderPage.searchResultClick();

        //customize ordered item module present
        assertTrue(customizeOrderedItemComponent.menuItemNamePresent("Pizza"));
        assertTrue(customizeOrderedItemComponent.menuItemPricePresent(25));
        customizeOrderedItemComponent.waitUntilIngredientsPresent();

        //deselect second ingredient
        customizeOrderedItemComponent.deselectActiveIngredient(2);

        //set quantity to 3
        customizeOrderedItemComponent.setQuantityInput(3);

        //set priority to 2
        customizeOrderedItemComponent.prioritySelectClick();
        customizeOrderedItemComponent.selectPriorityOption(2);

        //add customized ordered item to order
        customizeOrderedItemComponent.addCustomizedOrderedItemButtonClicked();

        //total cost changed to 75
        assertTrue(createOrderPage.totalCostPresent(75));

        //add some note
        createOrderPage.additionalNotesButtonClick();
        createOrderPage.setAdditionalNotesInput("Some note");
        createOrderPage.confirmAdditionNotesButtonClick();

        //confirm order
        createOrderPage.confirmCreateOrderButtonClick();

        //pay now or later -> later
        viewAndPayOrderComponent.payLaterButtonClick();

        //open table options for table 2
        waiterDashboardPage.waitUntilTablePresent();
        waiterDashboardPage.areaTableButtonClicked(2);
        assertTrue(tableOptionsComponent.tableNumberTitlePresent("Table 2"));

        //number of active orders increased by 1
        tableOptionsComponent.waitUntilOrderPresent();
        assertTrue(tableOptionsComponent.activeOrdersPresent(8));

    }

    @After
    public void closeSelenium() {
        browser.quit();
    }
}
