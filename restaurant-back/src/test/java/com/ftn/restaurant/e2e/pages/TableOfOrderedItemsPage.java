package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TableOfOrderedItemsPage {

    private WebDriver driver;
    private static String URL = "http://localhost:4200/bartender-dashboard";

    @FindBy(id = "name-Spaghetti")
    private WebElement rowTable;

    @FindBy(id = "btn7")
    private WebElement btnAccept;

    @FindBy(id = "Yess")
    private WebElement dialogAcceptBtn;

    public TableOfOrderedItemsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}


    public WebElement isRow() {
        return Utilities.visibilityWait(this.driver, this.rowTable, 40);
    }
    public void clickAccept(){
        Utilities.clickableWait(this.driver, this.btnAccept, 40).click();
    }
    public void clickAcceptDialog(){
        Utilities.clickableWait(this.driver, this.dialogAcceptBtn, 40).click();
    }
}
