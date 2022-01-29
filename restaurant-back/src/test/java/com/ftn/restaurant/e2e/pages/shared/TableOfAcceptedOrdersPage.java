package com.ftn.restaurant.e2e.pages.shared;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TableOfAcceptedOrdersPage {

    private WebDriver driver;
    private static String URL = "http://localhost:4200/bartender-dashboard";

    @FindBy(id = "name-Spaghetti")
    private WebElement rowTable;

    @FindBy(id = "btn7")
    private WebElement btnAccept;

    @FindBy(id = "Yess")
    private WebElement dialogAcceptBtn;

    public TableOfAcceptedOrdersPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}


    public WebElement isRow() {
        return Utilities.visibilityWait(this.driver, this.rowTable, 40);
    }
    public void clickFinish(){
        Utilities.clickableWait(this.driver, this.btnAccept, 40).click();
    }
    public void clickAcceptDialog(){
        Utilities.clickableWait(this.driver, this.dialogAcceptBtn, 40).click();
    }
}
