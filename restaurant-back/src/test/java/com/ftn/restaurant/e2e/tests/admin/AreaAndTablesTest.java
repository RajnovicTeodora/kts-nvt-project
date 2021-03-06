package com.ftn.restaurant.e2e.tests.admin;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.Utilities;
import com.ftn.restaurant.e2e.pages.admin.AreasPage;
import com.ftn.restaurant.e2e.pages.admin.EmployeesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

public class AreaAndTablesTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AreasPage areasPage;
    private EmployeesPage employeesPage;

    @Before
    public void setupSelenium() {
        /*System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\firefoxdriver.exe");
        driver = new FirefoxDriver();*/
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        driver = new ChromeDriver();
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

        driver.navigate().refresh();
        //check if area exists
        assertEquals(3, areasPage.countAreas());
        driver.navigate().refresh();
        //delete area
        areasPage.deleteAreaClick(2);
        areasPage.confirmDeleteClicked();

        driver.navigate().refresh();
        //check if deleted
        assertEquals(2, areasPage.countAreas());
    }

    @Test
    public void addSaveDeleteTableTest(){
        //login
        loginPage.login("admin", "test");
        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/admin-dashboard", 10));

        //open restaurant preview
        employeesPage.restaurantPreviewClick();

        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/edit-area", 20));

        areasPage.waitUntilAreaPresent();

        areasPage.clickOnArea(1);

        areasPage.addTableClick();
        areasPage.addTableClick();
        areasPage.addTableClick();

        areasPage.saveChangesClick();

        assertEquals(3, areasPage.countTables());

        areasPage.doubleClickTable(4);
        areasPage.deleteTableClick();
        areasPage.confirmDeleteClicked();

        areasPage.waitInvisibility();
        assertEquals(2, areasPage.countTables());

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
