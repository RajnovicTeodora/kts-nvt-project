package com.ftn.restaurant.e2e.tests.admin;

import com.ftn.restaurant.e2e.pages.shared.LoginPage;
import com.ftn.restaurant.e2e.pages.Utilities;
import com.ftn.restaurant.e2e.pages.admin.EditEmployeePage;
import com.ftn.restaurant.e2e.pages.admin.EmployeesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EditEmployeeTest {
    private WebDriver driver;

    private EmployeesPage employeesPage;
    private EditEmployeePage editEmployeePage;
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
        editEmployeePage = PageFactory.initElements(driver, EditEmployeePage.class);

    }

    @Test
    public void editEmployeeTest(){
        //login
        loginPage.setUsernameInput("admin");
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        String url = "http://localhost:4200/admin-dashboard";

        assertTrue(Utilities.urlWait(driver, url, 10));

        //click button to edit employee
        employeesPage.clickEditFirstUser();

        String newName = "Pera";
        String newSurname = "Peric";
        String newTelephone = "123456789";

        editEmployeePage.setInputText("name", newName);
        editEmployeePage.setInputText("surname", newSurname);
        editEmployeePage.setInputText("telephone", newTelephone);

        editEmployeePage.saveBttnClick();
        driver.navigate().refresh();
        assertTrue(employeesPage.infoChanged(newName, newSurname, newTelephone));

    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        driver.quit();
    }


}