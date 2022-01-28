package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MenuViewPage {
    private WebDriver driver;

    @FindBy(id = "item-search")
    private WebElement menuItemSearchInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "add-drink-button")
    private WebElement addDrinkButton;

    @FindBy(xpath = "//*[@class='mat-card-title']")
    private List<WebElement> menuItemTitleTexts;

    @FindBy(xpath = "//input[@id='price']")
    private WebElement menuItemPrice;

    @FindBy(xpath = "//input[@id='purchase-price']")
    private WebElement menuItemPurchasePrice;

    @FindBy(xpath = "//form[@id='price-form']//*//button")
    private WebElement savePricesButton;

    @FindBy(xpath = "//*[@class='mat-card-actions']//button[span[*[contains(text(), 'check_box_outline_blank')]]]")
    private List<WebElement> setOnMenuButtons;

    @FindBy(xpath = "//*[@class='mat-card-actions']//button[span[*[contains(text(), 'delete')]]]")
    private List<WebElement> deleteItemButton;

    public MenuViewPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getMenuItemSearchInput() {
        return Utilities.visibilityWait(driver, this.menuItemSearchInput, 10);
    }

    public void setMenuItemSearchInput(String text) {
        WebElement we = getMenuItemSearchInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }

    public boolean menuItemsTitleTextsSizeCompare(int number) {
        return this.menuItemTitleTexts.size() == number;
    }

    public boolean titleInMenuItemTitleTexts(String text) {
        for (WebElement item : menuItemTitleTexts) {
            if (item.getText().toLowerCase().contains(text.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean menuItemsContainText(String text) {
        for (WebElement item : menuItemTitleTexts) {
            if (!item.getText().toLowerCase().contains(text.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public void waitUntilItemsPresent() {
        Utilities.visibilityWaitByLocator(driver, By.className("card-deck"), 10);
    }

    public WebElement getMenuItemPrice() {
        return Utilities.visibilityWait(driver, this.menuItemPrice, 10);
    }

    public void setMenuItemPrice(String text) {
        WebElement we = getMenuItemPrice();
        we.clear();
        we.sendKeys(text);
    }

    public WebElement getMenuItemPurchasePrice() {
        return Utilities.visibilityWait(driver, this.menuItemPurchasePrice, 10);
    }

    public void setMenuItemPurchasePrice(String text) {
        WebElement we = getMenuItemPurchasePrice();
        we.clear();
        we.sendKeys(text);
    }

    public void savePricesBtnClick() {
        Utilities.clickableWait(driver, this.savePricesButton, 10).click();
    }

    public boolean isSetOnMenuEnabled(int index){
        return Boolean.parseBoolean(this.setOnMenuButtons.get(index).getAttribute("ng-reflect-disabled"));
    }

    public void setOnMenuBtnClick(int index) {
        Utilities.clickableWait(driver, this.setOnMenuButtons.get(index), 10).click();
    }

    public void deleteBtnClick(int index) {
        Utilities.clickableWait(driver, this.deleteItemButton.get(index), 10).click();
    }

    public void addDrinkBtnClick() {
        Utilities.clickableWait(driver, this.addDrinkButton, 10).click();
    }

}
