package com.ftn.restaurant.e2e.tests;

import com.ftn.restaurant.e2e.pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class DrinkTableTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private BartenderDashboardPage bartenderDashboardPage;
    private DrinkTablePage drinkTablePage;

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
        drinkTablePage = PageFactory.initElements(browser, DrinkTablePage.class);


    }

    @Test
    public void drinkTableTest() {

        // set username
        loginPage.setUsernameInput("misko");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(bartenderDashboardPage.urlPresent());

        //input new password
//        bartenderDashboardPage.setInputNewPassword("test");
//        bartenderDashboardPage.clickSubmitBtn();

        assertTrue(bartenderDashboardPage.urlPresent());

        bartenderDashboardPage.clickDrinkTableBtn();
        //clicked on drinkTable
        drinkTablePage.setSearchInput("Cockta");
        drinkTablePage.clickSubmitBtn();
        try{
        assertTrue(drinkTablePage.getRow().getText().equals("Cockta"));}
        catch(org.openqa.selenium.StaleElementReferenceException ex){
            assertTrue(drinkTablePage.getRow().getText().equals("Cockta"));
        }

        drinkTablePage.setSearchInput("Cockta");
        drinkTablePage.setRoleFilter();
        drinkTablePage.clickSubmitBtn();
        try{
            assertTrue(drinkTablePage.getRow().getText().equals("Cockta"));}
        catch(org.openqa.selenium.StaleElementReferenceException ex){
            assertTrue(drinkTablePage.getRow().getText().equals("Cockta"));
        }

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        //browser.quit();
    }
}
