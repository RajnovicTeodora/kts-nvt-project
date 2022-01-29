package com.ftn.restaurant.e2e.pages.manager;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ItemViewPage {
    private WebDriver driver;

    @FindBy(id = "item-search")
    private WebElement itemSearchInput;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class='card-deck']//mat-card-title")
    private List<WebElement> itemTitleTexts;

    @FindBy(xpath = "//div[@class='card-deck']//button[span[contains(text(),'Approve')]]")
    private List<WebElement> approveItemButtons;

    @FindBy(xpath = "//div[@class='card-deck']//button[span[contains(text(),'Delete')]]")
    private List<WebElement> deleteItemButton;

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

    public void submitBtnClick() {
        Utilities.clickableWait(driver, this.submitButton, 10).click();
    }

    public boolean itemsTitleTextsSizeCompare(int number) {
        return this.itemTitleTexts.size() == number;
    }

    public boolean itemsTitleTextsContainText(String text) {
        for (WebElement item : itemTitleTexts) {
            if (!item.getText().toLowerCase().contains(text)) {
                return false;
            }
        }
        return true;
    }

    public void waitUntilItemsPresent() {
        Utilities.visibilityWaitByLocator(driver, By.className("card-deck"), 20);
    }

    public void approveBtnClick(int index){
        Utilities.clickableWait(driver, this.approveItemButtons.get(index), 10).click();
    }

    public void deleteBtnClick(int index){
        Utilities.clickableWait(driver, this.deleteItemButton.get(index), 10).click();
    }
}
