package com.ftn.restaurant.e2e.tests.admin;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.Utilities;
import com.ftn.restaurant.e2e.pages.admin.AddEmployeePage;
import com.ftn.restaurant.e2e.pages.admin.EmployeesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteEmployeeTest {

    private WebDriver driver;

    private EmployeesPage employeesPage;
    private AddEmployeePage addEmployeePage;
    private LoginPage loginPage;

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
        employeesPage = PageFactory.initElements(driver, EmployeesPage.class);
        addEmployeePage = PageFactory.initElements(driver, AddEmployeePage.class);
    }

    @Test
    public void deleteEmployeeTest(){

        //login
        loginPage.login("admin", "test");
        assertTrue(Utilities.urlWait(driver, "http://localhost:4200/admin-dashboard", 10));

        String username = "chef";
        String password = "test";

        //delete employee
        employeesPage.clickDeleteTable(username);
        driver.navigate().refresh();
        assertTrue(employeesPage.confirmUserDeleted(username));

        //logout
        employeesPage.logoutBtnClick();
        employeesPage.confirmLogoutBtnClick();

        //try login
        loginPage.login(username, password);

        assertEquals("User not found.", employeesPage.getToastrMessage());
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }
}
