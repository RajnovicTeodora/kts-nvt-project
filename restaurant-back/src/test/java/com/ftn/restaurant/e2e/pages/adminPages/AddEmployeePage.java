package com.ftn.restaurant.e2e.pages.adminPages;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class AddEmployeePage {

    private WebDriver driver;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "surname")
    private WebElement surnameInput;

    @FindBy(id = "telephone")
    private WebElement telephoneInput;

    @FindBy(xpath = "//span[contains(text(),'Waiter')]")
    private WebElement roleOption;

    @FindBy(id = "role")
    private WebElement roleContainer;

    @FindBy(id = "save-button")
    private WebElement saveBttn;

    @FindBy(xpath = "//td[contains(@class,'cdk-column-Username')]")
    private List<WebElement> usernames;

    public AddEmployeePage(WebDriver driver){
        this.driver = driver;
    }

    private WebElement getInputField(String field){
        switch(field){
            case "username":
                return Utilities.visibilityWait(driver, this.usernameInput, 10);
            case "password":
                return Utilities.visibilityWait(driver, this.passwordInput, 10);
            case "name":
                return Utilities.visibilityWait(driver, this.nameInput, 10);
            case "surname":
                return Utilities.visibilityWait(driver, this.surnameInput, 10);
            case "telephone":
                return Utilities.visibilityWait(driver, this.telephoneInput, 10);
            default:
                return null;
        }
    }

    public void setInputText(String field, String text){
        WebElement el = getInputField(field);
        el.clear();
        el.sendKeys(text);
    }

    public void saveBttnClick() {
        Utilities.clickableWait(driver, this.saveBttn, 10).click();
    }

    public boolean usernamesContainsText(String text){
        List<WebElement> usernames = Utilities.visibilityWait(driver, By.name("username"), 30);
        for (WebElement username : usernames){
            if(username.getText().equals(text)){
                return true;
            }
        }
        return false;
    }

    public void setRole() {
        Utilities.clickableWait(driver, this.roleContainer, 10).click();
        Utilities.clickableWait(driver, this.roleOption, 10).click();
    }

}
