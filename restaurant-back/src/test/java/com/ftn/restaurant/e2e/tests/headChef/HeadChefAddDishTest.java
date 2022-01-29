package com.ftn.restaurant.e2e.tests.headChef;

import com.ftn.restaurant.e2e.pages.*;
import com.ftn.restaurant.e2e.pages.headChef.AddDishPageByHeadChefPage;
import com.ftn.restaurant.e2e.pages.headChef.HeadChefDashboardPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class HeadChefAddDishTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private HeadChefDashboardPage headChefDashboardPage;
    private AddDishPageByHeadChefPage addDishPage;

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
        headChefDashboardPage = PageFactory.initElements(browser, HeadChefDashboardPage.class);
        addDishPage = PageFactory.initElements(browser, AddDishPageByHeadChefPage.class);
    }

    @Test
    public void addDishTest() {

        // set username
        loginPage.setUsernameInput("slavkoo");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(headChefDashboardPage.urlPresent());

        assertTrue(headChefDashboardPage.urlPresent());

        //Add drink
        headChefDashboardPage.addDishBtnClick();
        addDishPage.setNameInput("Test");
        addDishPage.setDrinkType();
        addDishPage.setFileInput("C:\\Users\\rajta\\Desktop\\Screenshot_1.png");

        addDishPage.inputIngredientBtnClick();
        addDishPage.setNameIngredient("Test");
        addDishPage.radioBtnClick();
        addDishPage.saveIngredientBtnClick();

        assertTrue(addDishPage.getRowTableIngredient().getText().equals("Test"));


        addDishPage.submitDrinkBtnClick();
        if(!addDishPage.getMessage().getText().equals("true")){
            assertTrue(addDishPage.getMessage().getText().equals("true"));

        }

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        browser.quit();
    }
}
