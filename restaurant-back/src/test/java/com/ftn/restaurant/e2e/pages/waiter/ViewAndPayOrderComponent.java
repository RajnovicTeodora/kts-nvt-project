package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ViewAndPayOrderComponent {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id='received-amount-input']")//
    private WebElement receivedAmount;

    @FindBy(xpath = "//*[@id='left-amount-input']")
    private WebElement changeLeft;

    @FindBy(id = "total-cost-span")
    private WebElement totalCost;

    @FindBy(xpath = "//*[@id='order-is-paid-h2']")
    private WebElement orderIsPaidText;

    @FindBy(id = "pay-order-confirm-button")
    private WebElement payOrderButton;

    @FindBy(id = "pay-order-close-button")
    private WebElement closeViewAndPayOrderWindow;

    @FindBy(xpath = "//*[@id='form-holder-div']/mat-action-list/table//*[@name='deliver-order-button']")
    private List<WebElement> deliverOrderButtons;

    @FindBy(xpath = "//*[@id='form-holder-div']/mat-action-list/table//*[@name='ordered-item-status']")
    private List<WebElement> orderedItemStatusList;

    @FindBy(xpath = "//*[@id='form-holder-div']/mat-action-list/table//*[@name='ordered-item-price']")
    private List<WebElement> orderedItemPriceList;

    @FindBy(xpath = "//*[@id='form-holder-div']/mat-action-list/table//*[@name='ordered-item-name']")
    private List<WebElement> orderedItemNameList;

    @FindBy(xpath = "//*[@id='form-holder-div']/mat-action-list/table//*[@name='ordered-item-quantity']")
    private List<WebElement> orderedItemQuantityList;

    @FindBy(xpath = "//*[@id='pay-order-later-button']")
    private WebElement payLaterButton;

    public ViewAndPayOrderComponent(WebDriver driver){ this.driver = driver;}

    public void deliverOrderButtonClick(int itemNumber) { Utilities.clickableWait(driver, this.deliverOrderButtons.get(itemNumber-1), 10).click();}

    public WebElement getReceivedAmountInput() {
        return Utilities.visibilityWait(driver, this.receivedAmount, 10);
    }

    public boolean totalCostPresent(int totalCost){ return Utilities.textWait(driver, this.totalCost, String.valueOf(totalCost), 10);}

    public void payOrderButtonClick() { Utilities.clickableWait(driver, this.payOrderButton, 10).click();}

    public void payLaterButtonClick() { Utilities.clickableWait(driver, this.payLaterButton, 10).click();}

    public void closeViewAndPayOrderWindowClick() { Utilities.clickableWait(driver, this.closeViewAndPayOrderWindow, 10).click();}

    public void changeLeftClick(){
        Actions actions = new Actions(driver);
        actions.click(this.changeLeft);
        actions.build().perform();
    }

    public WebElement getChangeAmountLeft() {
        return Utilities.visibilityWait(driver, this.changeLeft, 10);
    }

    public boolean changeLeftPresent(int change){
        WebElement we = getChangeAmountLeft();
        String text = we.getAttribute("value");
        return text.equals(String.valueOf(change));
    }

    public boolean orderIsPaid(){ return driver.findElement(By.id("order-is-paid-h2")).isDisplayed();}

    public boolean numberOfActiveOrderedItems(int activeItems){
        return this.deliverOrderButtons.size() == activeItems;
    }

    public void waitUntilOrderedItemPresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@id='form-holder-div']/mat-action-list/table//*[@name='deliver-order-button'][1]"), 10);
    }

    public void waitUntilToastTextNotPresent(){
        Utilities.invisibilityWait(driver, 10, "//*[@id=\"toast-container\"]/div/div");
    }

    public void waitUntilIsPaidPresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@id='order-is-paid-h2']"), 10);
    }

    public void setReceivedAmountInput(int value) {
        WebElement we = getReceivedAmountInput();
        we.clear();
        we.sendKeys(String.valueOf(value));
    }

    public boolean containsItemsReadyToDeliver(int itemsToDeliver){
        String expectedTooltip = "Deliver ordered item.";
        int found = 0;
        for(WebElement itemButton : deliverOrderButtons){
            String actualTooltip = itemButton.getAttribute("matTooltip");
            if(actualTooltip.equals(expectedTooltip)) {
                found += 1;
            }
        }
        return found == itemsToDeliver;
    }

    public void deliverReadyOrderedItems(){
        String expectedTooltip = "Deliver ordered item.";
        for(WebElement itemButton : deliverOrderButtons){
            String actualTooltip = itemButton.getAttribute("matTooltip");
            if(actualTooltip.equals(expectedTooltip)) {
                Utilities.clickableWait(driver, itemButton, 10).click();
            }
        }
    }

    public boolean orderedItemPresent(String itemName, String status, int quantity, int price){
        for(int index = 0; index < this.orderedItemNameList.size(); index++){
            if(this.orderedItemNameList.get(index).getText().equals(itemName)
                    && this.orderedItemStatusList.get(index).getText().equals(status)
                    && this.orderedItemQuantityList.get(index).getText().equals(String.valueOf(quantity)+"x")
                    && this.orderedItemPriceList.get(index).getText().equals("$ "+String.valueOf(price))){
                return true;
            }
        }
        return false;
    }
}
