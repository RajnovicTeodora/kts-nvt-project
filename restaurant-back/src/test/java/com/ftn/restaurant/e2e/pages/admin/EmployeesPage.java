package com.ftn.restaurant.e2e.pages.admin;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EmployeesPage {

    private static final String URL = "http://localhost:4200/admin-dashboard";

    private WebDriver driver;

    @FindBy(className = "addBttn")
    private WebElement addEmployeeBttn;

    @FindBy(name = "name")
    private List<WebElement> names;

    @FindBy(name = "surname")
    private List<WebElement> surnames;

    @FindBy(name = "telephone")
    private List<WebElement> telephones;

    @FindBy(name = "deleteEmployeeBttn")
    private List<WebElement> deleteEmployeeBttns;

    @FindBy(id = "logoutBttn")
    private WebElement logoutButton;

    @FindBy(id = "confirm-logout-button")
    private WebElement confirmLogoutButton;

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
        List<WebElement> usernames = Utilities.visibilityWait(driver, By.name("username"), 20);
        return usernames.get(0).getText();
    }

    public boolean infoChanged(String name, String surname, String telephone){
        int userIndex = -1;
        List<WebElement> usernames = Utilities.visibilityWait(driver, By.name("username"), 20);
        for (WebElement user : usernames){
            if(user.getText().equals(getFirstUsername())){
                userIndex = usernames.indexOf(user);
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
        List<WebElement> editButtons = Utilities.visibilityWait(driver, By.id("editEmployee"), 20);
        Utilities.clickableWait(driver, editButtons.get(0), 10).click();
    }

    public void clickDeleteTable(String username){
        int userIndex = -1;
        List<WebElement> usernames = Utilities.visibilityWait(driver, By.name("username"), 20);
        for(WebElement user : usernames){
            if(user.getText().equals(username)){
                userIndex = usernames.indexOf(user);
            }
        }
        Utilities.clickableWait(driver, this.deleteEmployeeBttns.get(userIndex), 10).click();
        Utilities.clickableWait(driver, this.confirmDelete, 10).click();
    }

    public boolean confirmUserDeleted(String username){
        List<WebElement> usernames = Utilities.visibilityWait(driver, By.name("username"), 20);
        for(WebElement user : usernames){
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
