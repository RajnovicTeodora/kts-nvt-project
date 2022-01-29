package com.ftn.restaurant.e2e.pages.admin;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditEmployeePage {
    private WebDriver driver;

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "surname")
    private WebElement surnameInput;

    @FindBy(id = "telephone")
    private WebElement telephoneInput;

    @FindBy(id = "save-button")
    private WebElement saveBttn;

    public EditEmployeePage(WebDriver driver){
        this.driver = driver;
    }

    private WebElement getInputField(String field){
        switch(field){
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




}
