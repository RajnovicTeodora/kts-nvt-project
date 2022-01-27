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

    @FindBy(xpath = "//div[@class='card-deck']//mat-list//mat-card-title")
    private List<WebElement> menuItemTitleTexts;

    //TODO
    @FindBy(xpath = "//div[@class='card-deck']//mat-list//button[span[contains(text(),'Approve')]]")
    private List<WebElement> onMenuButtons;

    //TODO
    @FindBy(xpath = "//div[@class='card-deck']//mat-list//button[span[contains(text(),'Delete')]]")
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

    public boolean menuItemTitleTextsContain(int number) {
        return this.menuItemTitleTexts.size() == number;
    }

    public boolean menuItemsTitleTextsSizeCompare(int number) {
        return this.menuItemTitleTexts.size() == number;
    }

    public boolean titleInMenuItemTitleTexts(String text){
        for (WebElement item : menuItemTitleTexts) {
            if (item.getText().contains(text)) {
                return true;
            }
        }
        return false;
    }

    public boolean menuItemsContainText(String text) {
        for (WebElement item : menuItemTitleTexts) {
            if (!item.getText().toLowerCase().contains(text)) {
                return false;
            }
        }
        return true;
    }

    public void waitUntilItemsPresent() {
        Utilities.visibilityWaitByLocator(driver, By.className("card-deck"), 10);
    }

    public void onMenuBtnClick(int index){
        Utilities.clickableWait(driver, this.onMenuButtons.get(index), 10).click();
    }

    public void deleteBtnClick(int index){
        Utilities.clickableWait(driver, this.deleteItemButton.get(index), 10).click();
    }

}
