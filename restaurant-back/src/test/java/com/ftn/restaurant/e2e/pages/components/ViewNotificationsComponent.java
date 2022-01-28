package com.ftn.restaurant.e2e.pages.components;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ViewNotificationsComponent {
    private WebDriver driver;

    @FindBy(id="set-notification-inactive")
    public List<WebElement> setViewedButtons;

    @FindBy(id="mat-tab-label-0-0")
    public WebElement newNotifTab;

    @FindBy(id="mat-tab-label-0-1")
    public WebElement oldNotifTab;

    @FindBy(xpath = "//*[@id=\"mat-tab-content-0-1\"]/div/mat-action-list/table/tbody/tr")
    public List<WebElement> oldNotifTabContent;

    @FindBy(xpath = "//*[@id=\"mat-tab-content-0-0\"]/div/mat-action-list/table/tbody/tr")
    public List<WebElement> newNotifTabContent;

    public ViewNotificationsComponent(WebDriver driver){this.driver = driver;}

    public void setViewedNotificationButtonClicked() { Utilities.clickableWait(driver, this.setViewedButtons.get(0), 10).click();}

    public void oldNotifTabClicked() { Utilities.clickableWait(driver, this.oldNotifTab, 10).click();}

    public void newNotifTabClicked() { Utilities.clickableWait(driver, this.newNotifTab, 10).click();}

    public boolean oldNotifContentSize(int size){
        return (oldNotifTabContent.size()/2) == size;
    }

    public boolean newNotifContentSize(int size){
        return (newNotifTabContent.size()/2) == size;
    }
}
