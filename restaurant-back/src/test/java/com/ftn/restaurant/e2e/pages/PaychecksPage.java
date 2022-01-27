package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PaychecksPage {
    private WebDriver driver;

    @FindBy(id = "employee-search")
    private WebElement employeeSearchInput;

    @FindBy(id = "role-filter-input")
    private WebElement roleContainer;

    @FindBy(xpath = "//span[contains(text(),'Bartender')]")
    private WebElement roleOption;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(xpath = "//tbody//tr")
    private  List<WebElement> tableRows;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Username')]")
    private List<WebElement> usernames;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Role')]")
    private List<WebElement> roles;

    @FindBy(xpath = "//td//button")
    private List<WebElement> editPaycheckButton;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Paycheck')]")
    private List<WebElement> paychecks;

    public PaychecksPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getEmployeeSearchInput() {
        return Utilities.visibilityWait(driver, this.employeeSearchInput, 10);
    }

    public void setEmployeeSearchInput(String text) {
        WebElement we = getEmployeeSearchInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }

    public boolean tableRowsSizeCompare(int number) {
        return this.tableRows.size() == number;
    }

    public boolean usernamesContainText(String text) {
        for (WebElement username : this.usernames) {
            if (!username.getText().toLowerCase().contains(text)) {
                return false;
            }
        }
        return true;
    }

    public boolean rolesContainText(String text) {
        for (WebElement role : this.roles) {
            if (!role.getText().contains(text)) {
                return false;
            }
        }
        return true;
    }

    public void waitUntilTablePresent() {
        Utilities.visibilityWaitByLocator(driver, By.xpath("//table"), 10);
    }


    public void editPaycheckBtnClick(int index){
        Utilities.clickableWait(driver, this.editPaycheckButton.get(index), 10).click();
    }

    public void setRoleFilter() {
        Utilities.clickableWait(driver, this.roleContainer, 10).click();
        Utilities.clickableWait(driver, this.roleOption, 10).click();
    }

    public boolean comparePaychecks(String paycheck, int index){
        return this.paychecks.get(index).getText().equals(paycheck);
    }

}
