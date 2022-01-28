package com.ftn.restaurant.e2e.tests.admin;

import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.Utilities;
import com.ftn.restaurant.e2e.pages.admin.AreasPage;
import com.ftn.restaurant.e2e.pages.admin.EmployeesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AreaAndTablesTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AreasPage areasPage;
    private EmployeesPage employeesPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\firefoxdriver.exe");
        driver = new FirefoxDriver();
        // maximize window
        driver.manage().window().maximize();
        // navigate
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        areasPage = PageFactory.initElements(driver, AreasPage.class);
        employeesPage = PageFactory.initElements(driver, EmployeesPage.class);
    }

    @Test
    public void addAndDeleteAreaTest(){

        //login
        loginPage.login("admin", "test");
        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/admin-dashboard", 10));

        //open restaurant preview
        employeesPage.restaurantPreviewClick();

        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/edit-area", 20));

        //add area
        areasPage.addAreaClick();
        areasPage.setNewAreaName("garden");
        areasPage.saveNewAreaClick();

        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/edit-area", 20));

        //check if area exists
        assertTrue(areasPage.checkAreaExists("garden"));

        //delete area
        areasPage.deleteAreaClick(2);

        //check if deleted
        assertFalse(areasPage.checkAreaExists("garden"));

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
