package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.controller.ReportController;
import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WaiterDashboardPage {
    private WebDriver driver;
    private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

    private static final String URL = "http://localhost:4200/waiter-dashboard";

    @FindBy(xpath = "//*[@id=\"notifications-button\"]")
    private WebElement notificationsButton;

    @FindBy(id = "change-password-button")
    private WebElement changePasswordButton;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(xpath = "//mat-list-item")
    private List<WebElement> activeAccountsButtons;

    @FindBy(id = "other-account-button")
    private WebElement otherAccountButton;

    @FindBy(id = "current-user")
    private WebElement currentUsername;

    @FindBy(xpath = "//*[@id=\"toast-container\"]/div/div")
    private WebElement toastText;

    @FindBy(xpath = "//*[@id=\"area-tables-container\"]/button")
    private List<WebElement> areaTablesButtons;

    public WaiterDashboardPage(WebDriver driver){ this.driver = driver;}

    public boolean urlPresent() {
        return Utilities.urlWait(driver, URL, 10);
    }

    public void logoutBtnClick() {
        Utilities.clickableWait(driver, this.logoutButton, 10).click();
    }

    public void changePasswordButtonClick() {
        Utilities.clickableWait(driver, this.changePasswordButton, 10).click();
    }

    public void notificationsButtonClick() {
        Utilities.clickableWait(driver, this.notificationsButton, 10).click();
    }

    public void switchToActiveAccount() { Utilities.clickableWait(driver, this.activeAccountsButtons.get(0), 10).click();   }

    public void otherAccountButtonClick() { Utilities.clickableWait(driver, this.otherAccountButton, 10).click();   }

    public boolean currentUsernamePresent(String name){ return Utilities.textWait(driver, this.currentUsername, name, 10);}

    public boolean activeAccountsSize(int size){  return activeAccountsButtons.size() == size;  }

    public boolean toastTextPresent(String text){ return Utilities.textWait(driver, this.toastText, text, 10);}

    public void areaTableButtonClicked(int tableNumber){
        Utilities.clickableWait(driver, this.areaTablesButtons.get(tableNumber-1), 10).click();}

    public void waitUntilTablePresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@id=\"area-tables-container\"]/button[1]"), 10);
    }
}
