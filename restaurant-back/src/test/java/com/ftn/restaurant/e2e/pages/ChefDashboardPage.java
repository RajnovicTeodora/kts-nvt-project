package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChefDashboardPage {
    private WebDriver driver;
    private static String URL = "http://localhost:4200/chef-dashboard";

    @FindBy(id = "password")
    private WebElement inputNewPassword;

    @FindBy(id = "submit-button")
    private WebElement submitPasswordBtn;

    @FindBy(id = "list-new-orders-fild")
    private WebElement newOrdersField;

    @FindBy(id = "list-acc-orders-fild")
    private WebElement acceptedOrdersField;

    @FindBy(id = "container-div")
    private WebElement containerWithOrders;

    public ChefDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}

    public void setInputNewPassword(String newPassword){
        WebElement input = this.getInputNewPassword();
        input.clear();
        input.sendKeys(newPassword);
    }
    public WebElement getInputNewPassword() {
        return Utilities.visibilityWait(this.driver, this.inputNewPassword, 40);
    }
    public void clickSubmitBtn(){
        Utilities.clickableWait(this.driver, this.submitPasswordBtn, 40).click();
    }

    public void clickNewOrders(){
        Utilities.clickableWait(this.driver, this.newOrdersField, 40).click();
    }

    public void clickAccOrders(){
        Utilities.clickableWait(this.driver, this.acceptedOrdersField, 40).click();
    }

    public WebElement getContainerWithOrders() {
        return Utilities.visibilityWait(this.driver, this.containerWithOrders, 40);
    }
}
