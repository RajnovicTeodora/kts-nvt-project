package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EditOrderPage {
    private WebDriver driver;

    private static final String URL = "http://localhost:4200/edit-order";

    @FindBy(id = "cancel-edit-order-button")
    private WebElement cancelCreateOrderButton;

    @FindBy(xpath = "//*[@id=\"search-result-button\"]")
    private WebElement searchResult;

    @FindBy(xpath = "//*[@id=\"total-order-cost\"]")
    private WebElement totalCost;

    @FindBy(xpath = "//*[@id=\"additional-notes-button\"]")
    private WebElement additionalNotesButton;

    @FindBy(id = "confirm-edit-order-button")
    private WebElement confirmEditedOrderButton;

    @FindBy(xpath = "//*[@id='additional-notes-input']")
    private WebElement additionNotesInput;

    @FindBy(xpath = "//*[@id='confirm-additional-notes-button']")
    private WebElement confirmAdditionNotesButton;

    @FindBy(xpath = "//*[@id='close-additional-notes-button']")
    private WebElement closeAdditionNotesButton;

    @FindBy(xpath = "//*[@id='confirm-action-button']")
    private WebElement confirmActionButton;

    @FindBy(xpath = "//*[@name='delete-ordered-item']")
    private List<WebElement> deleteOrderedItemButtons;

    @FindBy(xpath = "//*[@name='edit-ordered-item']")
    private List<WebElement> editOrderedItemButtons;

    @FindBy(xpath = "//*[@name='ordered-item-quantity']")
    private List<WebElement> orderedItemQuantities;

    @FindBy(xpath = "//*[@name='ordered-item-name']")
    private List<WebElement> orderedItemNames;

    @FindBy(xpath = "//*[@name='ordered-item-status']")
    private List<WebElement> orderedItemStatusList;

    public EditOrderPage(WebDriver driver){ this.driver = driver;}

    public void cancelCreateOrderButtonClick() { Utilities.clickableWait(driver, this.cancelCreateOrderButton, 10).click();}

    public void searchResultClick() { Utilities.clickableWait(driver, this.searchResult, 10).click();}

    public boolean totalCostPresent(int totalCost){ return Utilities.textWait(driver, this.totalCost, "$ " + String.valueOf(totalCost), 10);}

    public void additionalNotesButtonClick() { Utilities.clickableWait(driver, this.additionalNotesButton, 10).click();}

    public void confirmEditedOrderButtonClick() { Utilities.clickableWait(driver, this.confirmEditedOrderButton, 10).click();}

    public void confirmAdditionNotesButtonClick() { Utilities.clickableWait(driver, this.confirmAdditionNotesButton, 10).click();}

    public void closeAdditionNotesButtonClick() { Utilities.clickableWait(driver, this.closeAdditionNotesButton, 10).click();}

    public void confirmActionButtonClick() { Utilities.clickableWait(driver, this.confirmActionButton, 10).click();}

    public void deleteOrderedItemButtonClick(int tableRow) { Utilities.clickableWait(driver, this.deleteOrderedItemButtons.get(tableRow-1), 10).click();}

    public void editOrderedItemButtonClick(int tableRow) { Utilities.clickableWait(driver, this.editOrderedItemButtons.get(tableRow-1), 10).click();}

    public boolean orderedItemPresent(String itemName, String status, int quantity){
        for(int index = 0; index < this.orderedItemNames.size(); index++){
            if(this.orderedItemNames.get(index).getText().equals(itemName)
            && this.orderedItemStatusList.get(index).getText().equals(status)
            && this.orderedItemQuantities.get(index).getText().equals(String.valueOf(quantity))){
                return true;
            }
        }
        return false;
    }

    public WebElement getAdditionalNotesInput() {
        return Utilities.visibilityWait(driver, this.additionNotesInput, 10);
    }

    public void setAdditionalNotesInput(String note) {
        WebElement we = getAdditionalNotesInput();
        we.clear();
        we.sendKeys(note);
    }

    public boolean additionalNotesContainsText(String notePresent){
        WebElement we = getAdditionalNotesInput();
        String text = we.getAttribute("ng-reflect-value");
        return text.equals(notePresent);
    }

    public boolean urlPresent(int orderNumber) {
        return Utilities.urlWait(driver, URL + "/" + orderNumber, 10);
    }

    public void waitUntilOldResultsNotPresent(){
        Utilities.invisibilityWait(driver, 10, "//*[@name='dish']");
    }

    public void waitUntilOrderedItemsPresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@name='ordered-item-name'][1]"), 10);
    }

    public void waitUntilAdditionalNotesPresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@id='additional-notes-input']"), 10);
    }

    public boolean numberOfOrderedItemsPresent(int orderedItemsNumber){
        return this.editOrderedItemButtons.size() == orderedItemsNumber;
    }

}
