package com.ftn.restaurant.e2e.tests.headChef;

import com.ftn.restaurant.e2e.pages.headChef.HeadChefDashboardPage;
import com.ftn.restaurant.e2e.pages.shared.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class HeadChefFinishOrder {
    private WebDriver browser;

    private LoginPage loginPage;
    private HeadChefDashboardPage chefDashboardPage;
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
        chefDashboardPage = PageFactory.initElements(browser, HeadChefDashboardPage.class);
        newOrdersPage = PageFactory.initElements(browser, NewOrdersPage.class);
        tableOfOrderedItemsPage = PageFactory.initElements(browser, TableOfOrderedItemsPage.class);
        acceptedOrdersPage = PageFactory.initElements(browser, AcceptedOrdersPage.class);
        tableOfAcceptedOrdersPage = PageFactory.initElements(browser, TableOfAcceptedOrdersPage.class);
        //todo dokaz da je gotovo
    }

    @Test
    public void finishOrderTest() {

        // set username
        loginPage.setUsernameInput("slavkoo");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(chefDashboardPage.urlPresent());

        assertTrue(chefDashboardPage.urlPresent());

        //click on new orders
        chefDashboardPage.clickNewOrders();
        assertTrue(chefDashboardPage.getContainerWithOrders()!=null);

        //new orders list
        assertTrue(newOrdersPage.isTitleOfOrder().getText().equals("Order number: 1"));
        newOrdersPage.clickFirstOrder();

        //table of ordered items
        assertTrue(tableOfOrderedItemsPage.isRow().getText().equals("Spaghetti"));
        tableOfOrderedItemsPage.clickAccept();

        //accepted
        tableOfOrderedItemsPage.clickAcceptDialog();
        assertTrue(this.browser.getCurrentUrl().equals("http://localhost:4200/head-chef-dashboard"));


        chefDashboardPage.clickAccOrders();
        assertTrue(this.browser.getCurrentUrl().equals("http://localhost:4200/head-chef-dashboard"));

        browser.navigate().refresh();
        chefDashboardPage.clickAccOrders();
        assertTrue(chefDashboardPage.getContainerWithOrders()!=null);

        assertTrue(acceptedOrdersPage.isTitleOfOrder().getText().equals("Order number: 1"));
        acceptedOrdersPage.clickFirstOrder();

        assertTrue(tableOfAcceptedOrdersPage.isRow().getText().equals("Spaghetti"));
        tableOfAcceptedOrdersPage.clickFinish();
        //finish
        tableOfAcceptedOrdersPage.clickAcceptDialog();
        assertTrue(this.browser.getCurrentUrl().equals("http://localhost:4200/head-chef-dashboard"));
        //nesto logicno
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        browser.quit();
    }
}
