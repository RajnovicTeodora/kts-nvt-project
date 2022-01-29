package com.ftn.restaurant.e2e.pages.manager;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ReportsPage {
    private WebDriver driver;

    @FindBy(id = "income-and-sold-button")
    private WebElement incomeAndSoldButton;

    @FindBy(id = "paychecks-button")
    private WebElement paychecksButton;

    @FindBy(id = "preparation-costs-button")
    private WebElement preparationCostsButton;

    @FindBy(id = "monthly-button")
    private WebElement monthlyButton;

    @FindBy(id = "quarterly-button")
    private WebElement quarterlyButton;

    @FindBy(id = "annual-button")
    private WebElement annualButton;

    @FindBy(xpath = "//*[@class='apexcharts-series']")
    private List<WebElement> seriesContainers;

    @FindBy(xpath = "//*[@class='apexcharts-series']//*")
    private List<WebElement> series;

    @FindBy(xpath = "//*[@class='apexcharts-xaxis-texts-g']//*//*[@id]")
    private List<WebElement> xAxisText;

    public ReportsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void incomeAndSoldBtnClick(){
        Utilities.clickableWait(driver, this.incomeAndSoldButton, 10).click();
    }
    public void paychecksBtnClick(){
        Utilities.clickableWait(driver, this.paychecksButton, 10).click();
    }
    public void preparationCostsBtnClick(){
        Utilities.clickableWait(driver, this.preparationCostsButton, 10).click();
    }
    public void monthlyBtnClick(){
        Utilities.clickableWait(driver, this.monthlyButton, 10).click();
    }
    public void quarterlyBtnClick(){
        Utilities.clickableWait(driver, this.quarterlyButton, 10).click();
    }
    public void annualBtnClick(){
        Utilities.clickableWait(driver, this.annualButton, 10).click();
    }

    public void waitUntilReportPresent(){
        Utilities.visibilityWaitByLocator(driver, By.className("content"), 10);
    }

    public boolean seriesContainersCountEqual(int number) {

        return seriesContainers.size() == number;
    }

    public boolean seriesContainsText(String[] text) {
        for(int i = 0; i < text.length; i++){
            if (!series.get(i).getAttribute("val").equals(text[i]))
                return false;
        }
        return true;
    }

    public boolean xAxisContainsText(String[] text) {
        for(int i = 0; i < text.length; i++){
            if (!xAxisText.get(i).getText().equals(text[i]))
                return false;
        }
        return true;
    }

}
