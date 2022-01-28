package com.ftn.restaurant.e2e.pages.components;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ChangePasswordComponent {
    private WebDriver driver;

    @FindBy(id = "password")
    private WebElement newPasswordInput;

    @FindBy(id = "submit-button")
    private WebElement firstPasswordChangeConfirm;

    @FindBy(id="regular-change-password-button")
    private WebElement regularPasswordChangeConfirm;

    public ChangePasswordComponent(WebDriver driver){this.driver = driver;}

    public void firstPasswordChangeConfirmClick() { Utilities.clickableWait(driver, this.firstPasswordChangeConfirm, 10).click();   }

    public void regularPasswordChangeConfirmClick() { Utilities.clickableWait(driver, this.regularPasswordChangeConfirm, 10).click();   }

    public WebElement getNewPasswordInput() {
        return Utilities.visibilityWait(driver, this.newPasswordInput, 40);
    }

    public void setNewPasswordInput(String text) {
        WebElement we = getNewPasswordInput();
        we.clear();
        we.sendKeys(text);
    }
}
