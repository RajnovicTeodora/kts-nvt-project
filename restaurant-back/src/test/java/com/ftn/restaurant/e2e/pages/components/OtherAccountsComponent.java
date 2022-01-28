package com.ftn.restaurant.e2e.pages.components;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OtherAccountsComponent {
    private WebDriver driver;

    @FindBy(id="other-account-login-btn")
    private WebElement otherAccountLoginBtn;

    public OtherAccountsComponent(WebDriver driver){this.driver = driver;}

    public void otherAccountLoginBtnClick() { Utilities.clickableWait(driver, this.otherAccountLoginBtn, 10).click();}


}
