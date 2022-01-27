package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ManagerDashboardPage {
    private WebDriver driver;

    private static final String URL = "http://localhost:4200/manager-dashboard";

    @FindBy(xpath="//mat-toolbar//button")
    private WebElement menuButton;

    // Side menu buttons
    @FindBy(xpath="//mat-list-item")
    private List<WebElement> listItemButtons;

    public ManagerDashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean urlPresent(){ return Utilities.urlWait(driver, URL, 10);}

    public void itemsViewBtnClick(){
        Utilities.clickableWait(driver, this.listItemButtons.get(0), 10).click();
    }

    public void menuViewBtnClick(){
        Utilities.clickableWait(driver, this.listItemButtons.get(1), 10).click();
    }

    public void paychecksViewBtnClick(){
        Utilities.clickableWait(driver, this.listItemButtons.get(2), 10).click();
    }

    public void reportsViewBtnClick(){
        Utilities.clickableWait(driver, this.listItemButtons.get(3), 10).click();
    }
}
