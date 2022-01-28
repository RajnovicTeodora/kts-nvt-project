package com.ftn.restaurant.e2e.tests;

import com.ftn.restaurant.e2e.pages.ChefDashboardPage;
import com.ftn.restaurant.e2e.pages.DishTablePage;
import com.ftn.restaurant.e2e.pages.LoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertTrue;

public class ViewOneDishChefTest {

    private WebDriver browser;

    private LoginPage loginPage;
    private ChefDashboardPage chefDashboardPage;
    private DishTablePage dishTablePage;

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
        chefDashboardPage = PageFactory.initElements(browser, ChefDashboardPage.class);
        dishTablePage = PageFactory.initElements(browser, DishTablePage.class);


    }

    @Test
    public void oneDishElementsViewTest() {

        // set username
        loginPage.setUsernameInput("chef");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(chefDashboardPage.urlPresent());

        assertTrue(chefDashboardPage.urlPresent());

        chefDashboardPage.clickDishTableBtn();
        //clicked on view for pizza
        dishTablePage.clickPizzaViewBtn();
        assertTrue(dishTablePage.getRowIngredient().getText().equals("plazma"));


    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        //browser.quit();
    }
}
