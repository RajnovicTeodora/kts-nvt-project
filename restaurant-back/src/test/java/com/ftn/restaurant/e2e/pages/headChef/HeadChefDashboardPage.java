package com.ftn.restaurant.e2e.pages.headChef;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeadChefDashboardPage {
    private WebDriver driver;
    private static String URL = "http://localhost:4200/head-chef-dashboard";

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
    @FindBy(id = "add-dish")
    private WebElement addDishBtn;

    public HeadChefDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 50);}

    public void setInputNewPassword(String newPassword){
        WebElement input = this.getInputNewPassword();
        input.clear();
        input.sendKeys(newPassword);
    }
    public WebElement getInputNewPassword() {
        return Utilities.visibilityWait(this.driver, this.inputNewPassword, 50);
    }
    public void clickSubmitBtn(){
        Utilities.clickableWait(this.driver, this.submitPasswordBtn, 50).click();
    }

    public void clickNewOrders(){
        Utilities.clickableWait(this.driver, this.newOrdersField, 50).click();
    }

    public void clickAccOrders(){
        Utilities.clickableWait(this.driver, this.acceptedOrdersField, 50).click();
    }

    public WebElement getContainerWithOrders() {
        return Utilities.visibilityWait(this.driver, this.containerWithOrders, 50);
    }

    public void addDishBtnClick() { Utilities.clickableWait(this.driver, this.addDishBtn, 50).click();
    }
}
