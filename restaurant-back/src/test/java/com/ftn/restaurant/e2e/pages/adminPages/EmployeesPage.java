package com.ftn.restaurant.e2e.pages.adminPages;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EmployeesPage {

    private static final String URL = "http://localhost:4200/admin-dashboard";

    private WebDriver driver;

    @FindBy(id = "employee-search")
    private WebElement searchInputField;

    @FindBy(id = "role-filter-input")
    private WebElement roleSelectField;

    @FindBy(id = "submit-button")
    private WebElement searchBttn;

    @FindBy(className = "addBttn")
    private WebElement addEmployeeBttn;

    //tabela usera (mozda nece trebati)

    @FindBy(name = "editEmployeeBttn")
    private List<WebElement> editEmployeeBttns;

    @FindBy(name = "deleteEmployeeBttn")
    private List<WebElement> deleteEmployeeBttns;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "confirm-logout-button")
    private WebElement confirmLogoutButton;


    public EmployeesPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent() {
        return Utilities.urlWait(driver, URL, 10);
    }

    public WebElement getEmployeeSearchInput() {
        return Utilities.visibilityWait(driver, this.searchInputField, 10);
    }

    public void setEmployeeSearchInput(String text) {
        WebElement we = getEmployeeSearchInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.searchBttn, 10).click();
    }


    public void logoutBtnClick() {
        Utilities.clickableWait(driver, this.logoutButton, 10).click();
    }

    public void confirmLogoutBtnClick() {
        Utilities.clickableWait(driver, this.confirmLogoutButton, 10).click();
    }

    public void addEmployeeBttnClicked(){
        Utilities.clickableWait(driver, this.addEmployeeBttn, 10).click();
    }


}
