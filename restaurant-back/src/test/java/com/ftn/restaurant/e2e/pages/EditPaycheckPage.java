package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditPaycheckPage {
    private WebDriver driver;

    @FindBy(id = "paycheck-input")
    private WebElement paycheckInput;

    @FindBy(id = "submit-paycheck-button")
    private WebElement submitPaycheckButton;

    public EditPaycheckPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getPaycheckInput() {
        return Utilities.visibilityWait(driver, this.paycheckInput, 10);
    }

    public void setPaycheckInput(String text) {
        WebElement we = getPaycheckInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.submitPaycheckButton, 10).click();
    }
}
