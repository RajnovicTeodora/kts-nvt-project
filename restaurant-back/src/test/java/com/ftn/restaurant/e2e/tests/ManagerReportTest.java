package com.ftn.restaurant.e2e.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ftn.restaurant.e2e.pages.LoginPage;
import com.ftn.restaurant.e2e.pages.ManagerDashboardPage;
import com.ftn.restaurant.e2e.pages.ReportsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ManagerReportTest {
    private WebDriver browser;

    private LoginPage loginPage;
    private ManagerDashboardPage managerDashboardPage;
    private ReportsPage reportsPage;

    @Before
    public void setupSelenium() {
        // instantiate browser
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        options.setCapability("excludeSwitches", "enable-automation");
        options.setCapability("useAutomationExtension", false);

        browser = new ChromeDriver();
        // maximize window
        //browser.manage().window().maximize();
        // navigate
        browser.navigate().to("http://localhost:4200/");

        loginPage = PageFactory.initElements(browser, LoginPage.class);
        managerDashboardPage = PageFactory.initElements(browser, ManagerDashboardPage.class);
        reportsPage = PageFactory.initElements(browser, ReportsPage.class);

    }

    @Test
    public void managerReportTest() {

        // set username
        loginPage.setUsernameInput("manager");

        // set correct password
        loginPage.setPasswordInput("test");
        loginPage.loginBtnClick();

        assertTrue(managerDashboardPage.urlPresent());

        managerDashboardPage.menuViewBtnClick();
        managerDashboardPage.reportsViewBtnClick();

        // Test if default report is showing
        // Two series: income and sold items
        assertTrue(reportsPage.seriesContainersCountEqual(2));

        String[] incomeAndSoldMonthly = new String[] {"2", "0", "3", "0", "1", "10", "0", "23", "0", "5"};
        assertTrue(reportsPage.seriesContainsText(incomeAndSoldMonthly));

        String[] incomeAndSoldMonthlyXAxis = new String[] {"2021-08", "2021-09", "2021-10", "2021-11", "2021-12"};
        assertTrue(reportsPage.xAxisContainsText(incomeAndSoldMonthlyXAxis));

        // Test paychecks
        reportsPage.paychecksBtnClick();
        assertTrue(true);
        // One series: paychecks

        // Monthly
        String[] paychecksMonthly = new String[] {"20", "20", "40", "40"};
        assertTrue(reportsPage.seriesContainsText(paychecksMonthly));

        String[] paychecksMonthlyXAxis = new String[] {"2021-09", "2021-10", "2021-11", "2021-12"};
        assertTrue(reportsPage.xAxisContainsText(paychecksMonthlyXAxis));

        // Quarterly
        reportsPage.quarterlyBtnClick();
        assertTrue(reportsPage.seriesContainersCountEqual(1));
        String[] paychecksQuarterly= new String[] {"20", "100"};
        assertTrue(reportsPage.seriesContainsText(paychecksQuarterly));

        String[] paychecksQuarterlyXAxis = new String[] {"Q3-2021", "Q4-2021"};
        assertTrue(reportsPage.xAxisContainsText(paychecksQuarterlyXAxis));

        // Annual
        reportsPage.annualBtnClick();
        String[] paychecksAnnual= new String[] {"120"};
        assertTrue(reportsPage.seriesContainsText(incomeAndSoldMonthly));

        String[] paychecksAnnualXAxis = new String[] {"2021"};
        assertTrue(reportsPage.xAxisContainsText(incomeAndSoldMonthlyXAxis));
    }

    @After
    public void closeSelenium() {
        // Shutdown the browser
        //browser.quit();
    }

}
