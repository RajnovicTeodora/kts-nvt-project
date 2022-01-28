package com.ftn.restaurant.e2e.tests.adminTests;

import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.adminPages.AddEmployeePage;
import com.ftn.restaurant.e2e.pages.adminPages.EmployeesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddEmployeeTest {
    private WebDriver driver;

    private EmployeesPage employeesPage;
    private AddEmployeePage addEmployeePage;
    private LoginPage loginPage;

    @Before
    public void setupSelenium() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\firefoxdriver.exe");
        driver = new FirefoxDriver();
        // maximize window
        driver.manage().window().maximize();
        // navigate
        driver.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(driver, LoginPage.class);
        employeesPage = PageFactory.initElements(driver, EmployeesPage.class);
        addEmployeePage = PageFactory.initElements(driver, AddEmployeePage.class);
    }

    @Test
    public void addEmployeeTest(){

        //login
        loginPage.setUsernameInput("admin");
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();
        assertEquals("http://localhost:4200/admin-dashboard", driver.getCurrentUrl());

        //click button to add employee
        employeesPage.addEmployeeBttnClicked();

        addEmployeePage.setInputText("username", "waiter2");
        addEmployeePage.setInputText("password", "pass123");
        addEmployeePage.setInputText("name", "Pera");
        addEmployeePage.setInputText("surname", "Peric");
        addEmployeePage.setInputText("telephone", "123456");
        addEmployeePage.setRole();

        addEmployeePage.saveBttnClick();

        assertTrue(addEmployeePage.usernamesContainsText("waiter2"));
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }

}
