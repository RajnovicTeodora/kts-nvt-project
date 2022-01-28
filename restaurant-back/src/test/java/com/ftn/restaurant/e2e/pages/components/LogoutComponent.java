package com.ftn.restaurant.e2e.pages.components;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutComponent {
    private WebDriver driver;

    @FindBy(id="confirm-logout-button")
    private WebElement confirmLogoutButton;

    public LogoutComponent(WebDriver driver){ this.driver = driver;}

    public void confirmLogoutButtonClick() {
        Utilities.clickableWait(driver, this.confirmLogoutButton, 10).click();
    }

}
