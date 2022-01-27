package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DrinkTablePage {

    private WebDriver driver;
    private static String URL = "http://localhost:4200/bartender-dashboard";

    @FindBy(id = "type-filter-input")
    private WebElement roleContainer;

    @FindBy(xpath = "//span[contains(text(),'cold drink')]")
    private WebElement roleOption;

    @FindBy(id = "submit-button")
    private WebElement submitBtn;

    @FindBy(id = "itemSearch")
    private WebElement searchInput;

    @FindBy(id = "Cockta")
    private WebElement rowsInTableCockta;

    public DrinkTablePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}

    public void setSearchInput(String newPassword){
        WebElement input = this.getSearchInput();
        input.clear();
        input.sendKeys(newPassword);
    }
    public WebElement getSearchInput() {
        return Utilities.visibilityWait(this.driver, this.searchInput, 40);
    }

    public void setRoleFilter() {
        Utilities.clickableWait(driver, this.roleContainer, 10).click();
        Utilities.clickableWait(driver, this.roleOption, 10).click();
    }

    public void clickSubmitBtn(){
        Utilities.clickableWait(this.driver, this.submitBtn, 40).click();
    }

    public WebElement getRow() {
        return Utilities.visibilityWait(this.driver, this.rowsInTableCockta, 40);
    }
}
