package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateOrderPage {
    private WebDriver driver;

    private static final String URL = "http://localhost:4200/create-order";

    @FindBy(id = "cancel-create-order-button")
    private WebElement cancelCreateOrderButton;

    @FindBy(xpath = "//*[@id=\"search-result-button\"]")
    private WebElement searchResult;

    @FindBy(xpath = "//*[@id=\"total-order-cost\"]")
    private WebElement totalCost;

    @FindBy(xpath = "//*[@id=\"additional-notes-button\"]")
    private WebElement additionalNotesButton;

    @FindBy(id = "confirm-create-order-button")
    private WebElement confirmCreateOrderButton;

    @FindBy(xpath = "//*[@id='additional-notes-input']")
    private WebElement additionNotesInput;

    @FindBy(xpath = "//*[@id='confirm-additional-notes-button']")
    private WebElement confirmAdditionNotesButton;

    public CreateOrderPage(WebDriver driver){ this.driver = driver;}

    public void cancelCreateOrderButtonClick() { Utilities.clickableWait(driver, this.cancelCreateOrderButton, 10).click();}

    public void searchResultClick() { Utilities.clickableWait(driver, this.searchResult, 10).click();}

    public boolean totalCostPresent(int totalCost){ return Utilities.textWait(driver, this.totalCost, "$ " + String.valueOf(totalCost), 10);}

    public void additionalNotesButtonClick() { Utilities.clickableWait(driver, this.additionalNotesButton, 10).click();}

    public void confirmCreateOrderButtonClick() { Utilities.clickableWait(driver, this.confirmCreateOrderButton, 10).click();}

    public void confirmAdditionNotesButtonClick() { Utilities.clickableWait(driver, this.confirmAdditionNotesButton, 10).click();}

    public WebElement getAdditionalNotesInput() {
        return Utilities.visibilityWait(driver, this.additionNotesInput, 10);
    }

    public void setAdditionalNotesInput(String note) {
        WebElement we = getAdditionalNotesInput();
        we.clear();
        we.sendKeys(note);
    }

    public boolean urlPresent(int tableNumber) {
        return Utilities.urlWait(driver, URL + "/" + tableNumber, 10);
    }

    public void waitUntilOldResultsNotPresent(){
        Utilities.invisibilityWait(driver, 10, "//*[@name='dish']");
    }
}
