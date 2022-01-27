package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.controller.ReportController;
import com.ftn.restaurant.e2e.pages.Utilities;
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

    @FindBy(id = "notifications-button")
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

    public boolean activeAccountsSize(int size){
        LOG.info(String.valueOf(activeAccountsButtons.size()));
        return activeAccountsButtons.size() == size;
    }
}
