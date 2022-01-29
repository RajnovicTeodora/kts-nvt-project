package com.ftn.restaurant.e2e.tests.headChef;

import com.ftn.restaurant.e2e.pages.*;
import com.ftn.restaurant.e2e.pages.headChef.HeadChefDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class HeadChefAcceptOrderTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private HeadChefDashboardPage chefDashboardPage;
    private NewOrdersPage newOrdersPage;
    private TableOfOrderedItemsPage tableOfOrderedItemsPage;
    private AcceptedOrdersPage acceptedOrdersPage;

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
    }

    @Test
    public void acceptOrderTest() {

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
        //checking is accepted

        try {
            TimeUnit.SECONDS.sleep(2); //OBJASNICU ZASTO
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chefDashboardPage.clickAccOrders();
        assertTrue(chefDashboardPage.getContainerWithOrders()!=null);

        assertTrue(acceptedOrdersPage.isTitleOfOrder().getText().equals("Order number: 1"));
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        browser.quit();
    }
}
