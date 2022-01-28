package com.ftn.restaurant.e2e.tests;

import com.ftn.restaurant.e2e.pages.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class BartenderAddDrinkTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private BartenderDashboardPage bartenderDashboardPage;
    private AddDrinkPageByBartender addDrinkPage;

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
        addDrinkPage = PageFactory.initElements(browser, AddDrinkPageByBartender.class);
    }

    @Test
    public void addDrinkTest() {

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

        //Add drink
        bartenderDashboardPage.addDrinkBtnClick();
        addDrinkPage.setNameInput("Test");
        addDrinkPage.setDrinkType();
        addDrinkPage.setDrinkContainer();
        addDrinkPage.setFileInput("C:\\Users\\rajta\\Desktop\\Screenshot_1.png");

        addDrinkPage.inputIngredientBtnClick();
        addDrinkPage.setNameIngredient("Test");
        addDrinkPage.radioBtnClick();
        addDrinkPage.saveIngredientBtnClick();

        assertTrue(addDrinkPage.getRowTableIngredient().getText().equals("Test"));


        addDrinkPage.submitDrinkBtnClick();
        if(!addDrinkPage.getMessage().getText().equals("true")){
            assertTrue(addDrinkPage.getMessage().getText().equals("true"));
        }
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        //browser.quit();
    }
}
