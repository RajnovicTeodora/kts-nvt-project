package com.ftn.restaurant.e2e.tests.bartender;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.bartender.BartenderDashboardPage;
import com.ftn.restaurant.e2e.pages.bartender.DrinkTablePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ViewOneDrinkBartenderPage {
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
    public void oneDrinkCardTest() {

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

        bartenderDashboardPage.clickDrinkTableBtn();
        //clicked on drinkTable
        drinkTablePage.clickSpriteBtn();
        assertEquals(drinkTablePage.getTitleCard().getText(), "Sprite");

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        browser.quit();
    }
}
