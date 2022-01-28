package com.ftn.restaurant.e2e.pages.adminPages;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
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

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Name')]")
    private List<WebElement> names;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Surname')]")
    private List<WebElement> surnames;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Telephone')]")
    private List<WebElement> telephones;

    @FindBy(id = "editEmployee")
    private List<WebElement> editEmployeeBttns;

    @FindBy(name = "deleteEmployeeBttn")
    private List<WebElement> deleteEmployeeBttns;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "confirm-logout-button")
    private WebElement confirmLogoutButton;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Username')]")
    private List<WebElement> usernames;

    @FindBy(xpath = "//*[@id=\"toast-container\"]/div/div")
    private WebElement toastText;

    @FindBy(className = "btn-primary")
    private WebElement confirmDelete;


    public EmployeesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void logoutBtnClick() {
        Utilities.clickableWait(driver, this.logoutButton, 20).click();
    }

    public void confirmLogoutBtnClick() {
        Utilities.clickableWait(driver, this.confirmLogoutButton, 10).click();
    }

    public void addEmployeeBttnClicked(){
        Utilities.clickableWait(driver, this.addEmployeeBttn, 10).click();
    }

    private String getFirstUsername(){
        return Utilities.visibilityWait(driver, this.usernames.get(0), 10).getText();
    }

    public boolean infoChanged(String name, String surname, String telephone){
        int userIndex = -1;
        for (WebElement user : this.usernames){
            if(user.getText().equals(getFirstUsername())){
                userIndex = this.usernames.indexOf(user);
                break;
            }
        }
        if(userIndex == -1) return false;
        String currentName = Utilities.visibilityWait(driver, this.names.get(userIndex), 20).getText();
        String currentSurname = Utilities.visibilityWait(driver, this.surnames.get(userIndex), 20).getText();
        String currentTelephone = Utilities.visibilityWait(driver, this.telephones.get(userIndex), 20).getText();
        if (!currentName.equalsIgnoreCase(name)){
            return false;
        }
        if (!currentSurname.equals(surname)){
            return false;
        }
        if (!currentTelephone.equals(telephone)){
            return false;
        }
        return true;
    }

    public void clickEditFirstUser(){
        Utilities.clickableWait(driver, this.editEmployeeBttns.get(0), 10).click();
    }

    public void clickDeleteTable(String username){
        int userIndex = -1;
        this.usernames = Utilities.visibilityWait(driver, By.xpath("//td[contains(@class,'cdk-column-Username')]"), 20);
        for(WebElement user : this.usernames){
            if(user.getText().equals(username)){
                userIndex = this.usernames.indexOf(user);
            }
        }
        Utilities.clickableWait(driver, this.deleteEmployeeBttns.get(userIndex), 10).click();
        Utilities.clickableWait(driver, this.confirmDelete, 10).click();
    }

    public boolean confirmUserDeleted(String username){
        this.usernames = Utilities.visibilityWait(driver, By.xpath("//td[contains(@class,'cdk-column-Username')]"), 10);
        for(WebElement user : this.usernames){
            System.out.println(user.getText());
            if(user.getText().equals(username)){
                return false;
            }
        }
        return true;
    }

    public String getToastrMessage(){
        return Utilities.visibilityWait(driver, this.toastText, 10).getText();
    }
}