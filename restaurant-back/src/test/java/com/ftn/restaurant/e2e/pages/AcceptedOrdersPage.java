package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AcceptedOrdersPage {
    private WebDriver driver;
    private static String URL = "http://localhost:4200/bartender-dashboard";

    @FindBy(id = "title1")
    private WebElement titleOfFirstOrder;

    public AcceptedOrdersPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}


    public WebElement isTitleOfOrder() {
        return Utilities.visibilityWait(this.driver, this.titleOfFirstOrder, 40);
    }
    public void clickFirstOrder(){
        Utilities.clickableWait(this.driver, this.titleOfFirstOrder, 40).click();
    }

}
