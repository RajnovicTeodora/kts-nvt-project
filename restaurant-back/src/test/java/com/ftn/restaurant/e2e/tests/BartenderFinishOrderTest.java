package com.ftn.restaurant.e2e.tests;

import com.ftn.restaurant.e2e.pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class BartenderFinishOrderTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private BartenderDashboardPage bartenderDashboardPage;
    private NewOrdersPage newOrdersPage;
    private TableOfOrderedItemsPage tableOfOrderedItemsPage;
    private AcceptedOrdersPage acceptedOrdersPage;
    private TableOfAcceptedOrdersPage tableOfAcceptedOrdersPage;

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
        bartenderDashboardPage = PageFactory.initElements(browser, BartenderDashboardPage.class);
        newOrdersPage = PageFactory.initElements(browser, NewOrdersPage.class);
        tableOfOrderedItemsPage = PageFactory.initElements(browser, TableOfOrderedItemsPage.class);
        acceptedOrdersPage = PageFactory.initElements(browser, AcceptedOrdersPage.class);
        tableOfAcceptedOrdersPage = PageFactory.initElements(browser, TableOfAcceptedOrdersPage.class);
        //todo dokaz da je gotovo
    }

    @Test
    public void singInTest() {

        // set username
        loginPage.setUsernameInput("misko");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(bartenderDashboardPage.urlPresent());

        //input new password
        bartenderDashboardPage.setInputNewPassword("test");
        bartenderDashboardPage.clickSubmitBtn();

        assertTrue(bartenderDashboardPage.urlPresent());

        //click on new orders
        bartenderDashboardPage.clickNewOrders();
        assertTrue(bartenderDashboardPage.getContainerWithOrders()!=null);

        //new orders list
        assertTrue(newOrdersPage.isTitleOfOrder().getText().equals("Order number: 1"));
        newOrdersPage.clickFirstOrder();

        //table of ordered items
        assertTrue(tableOfOrderedItemsPage.isRow().getText().equals("Spaghetti"));
        tableOfOrderedItemsPage.clickAccept();
        //accepted
        tableOfOrderedItemsPage.clickAcceptDialog();
        assertTrue(this.browser.getCurrentUrl().equals("http://localhost:4200/bartender-dashboard"));
        //checking is accepted
        bartenderDashboardPage.clickNewOrders();
        bartenderDashboardPage.clickNewOrders();
        bartenderDashboardPage.clickAccOrders(); //ovo se dewsi prebrzo
        bartenderDashboardPage.clickAccOrders(); bartenderDashboardPage.clickAccOrders(); bartenderDashboardPage.clickAccOrders();
        assertTrue(bartenderDashboardPage.getContainerWithOrders()!=null);

        assertTrue(acceptedOrdersPage.isTitleOfOrder().getText().equals("Order number: 1"));
        acceptedOrdersPage.clickFirstOrder();

        assertTrue(tableOfAcceptedOrdersPage.isRow().getText().equals("Spaghetti"));
        tableOfAcceptedOrdersPage.clickFinish();
        //finish
        tableOfAcceptedOrdersPage.clickAcceptDialog();
        assertTrue(this.browser.getCurrentUrl().equals("http://localhost:4200/bartender-dashboard"));
        //nesto logicno
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        //browser.quit();
    }
}
