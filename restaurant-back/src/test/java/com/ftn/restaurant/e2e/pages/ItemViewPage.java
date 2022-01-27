package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ItemViewPage {
    private WebDriver driver;

    @FindBy(id = "item-search")
    private WebElement itemSearchInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public ItemViewPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getItemSearchInput() {
        return Utilities.visibilityWait(driver, this.itemSearchInput, 10);
    }

    public void setItemSearchInput(String text) {
        WebElement we = getItemSearchInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitBtnClick(){
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }


}
