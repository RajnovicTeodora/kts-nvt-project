package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ViewAndPayOrderComponent {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"mat-input-16\"]")
    private WebElement receivedAmount;

    @FindBy(xpath = "//*[@id=\"mat-input-17\"]")
    private WebElement changeLeft;

    @FindBy(id = "total-cost-span")
    private WebElement totalCost;

    @FindBy(id = "order-is-paid-h2")
    private WebElement orderIsPaidText;

    @FindBy(id = "pay-order-confirm-button")
    private WebElement payOrderButton;

    @FindBy(id = "pay-order-close-button")
    private WebElement closeViewAndPayOrderWindow;

    @FindBy(xpath = "//*[@id='form-holder-div']/mat-action-list/table//*[@name='deliver-order-button']")
    private List<WebElement> deliverOrderButtons;

    public ViewAndPayOrderComponent(WebDriver driver){ this.driver = driver;}

    public void deliverOrderButtonClick(int itemNumber) { Utilities.clickableWait(driver, this.deliverOrderButtons.get(itemNumber-1), 10).click();}

    public WebElement getReceivedAmountInput() {
        return Utilities.visibilityWait(driver, this.receivedAmount, 10);
    }

    public boolean totalCostPresent(int totalCost){ return Utilities.textWait(driver, this.totalCost, String.valueOf(totalCost), 10);}

    public void payOrderButtonClick() { Utilities.clickableWait(driver, this.payOrderButton, 10).click();}


    public void setReceivedAmountInput(int value) {
        WebElement we = getReceivedAmountInput();
        we.clear();
        we.sendKeys(String.valueOf(value));
    }

    public boolean containsItemsReadyToDeliverPresent(){
        String expectedTooltip = "Deliver ordered item.";
        boolean found = false;
        for(WebElement itemButton : deliverOrderButtons){
            String actualTooltip = itemButton.getAttribute("matTooltip");
            if(actualTooltip.equals(expectedTooltip)) {
                found = true;
            }
        }
        return found;
    }
}
