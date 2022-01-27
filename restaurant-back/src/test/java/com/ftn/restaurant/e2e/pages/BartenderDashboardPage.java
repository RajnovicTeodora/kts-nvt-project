package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BartenderDashboardPage {

    private WebDriver driver;
    private static String URL = "http://localhost:4200/bartender-dashboard";

    @FindBy(id = "password")
    private WebElement inputNewPassword;

    @FindBy(id = "submit-button")
    private WebElement submitPasswordBtn;

    public BartenderDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}

    public void setInputNewPassword(String newPassword){
        WebElement input = this.getInputNewPassword();
        input.clear();
        input.sendKeys(newPassword);
    }
    private WebElement getInputNewPassword() {
        return Utilities.visibilityWait(this.driver, this.inputNewPassword, 40);
    }
    public void clickSubmitBtn(){
        Utilities.clickableWait(this.driver, this.submitPasswordBtn, 40).click();
    }
}
