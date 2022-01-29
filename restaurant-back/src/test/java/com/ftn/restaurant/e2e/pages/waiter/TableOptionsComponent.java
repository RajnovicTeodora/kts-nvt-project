package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TableOptionsComponent {
    private WebDriver driver;

    @FindBy(id = "table-number-title")
    private WebElement tableNumberTitle;

    @FindBy(xpath = "//*[@id='table-claimed-status-text']")
    private WebElement tableClaimedStatusText;

    @FindBy(xpath = "//*[@id='table-claimed-button-container']/button")
    private WebElement tableClaimedStatusButton;

    @FindBy(xpath = "//*[@id='table-occupied-status-text']")
    private WebElement tableOccupiedStatusText;

    @FindBy(xpath = "//*[@id='table-occupied-button-container']/button")
    private WebElement tableOccupiedStatusButton;

    @FindBy(id = "active-orders-title")
    private WebElement activeOrdersTitle;

    @FindBy(xpath = "//*[@name='edit-order-button']")
    private List<WebElement> editActiveOrderButtons;

    @FindBy(xpath = "//*[@name='view-order-button']")
    private List<WebElement> viewActiveOrderButtons;

    @FindBy(xpath = "//*[@id=\"create-order-button\"]")
    private WebElement createOrderButton;

    @FindBy(xpath = "//*[@id=\"close-table-options-button\"]")
    private WebElement closeTableOptionsButton;

    public TableOptionsComponent(WebDriver driver){ this.driver = driver;}

    public boolean tableNumberTitlePresent(String tableNumber){ return Utilities.textWait(driver, this.tableNumberTitle, tableNumber, 10);}

    public boolean tableClaimedTextPresent(String claimedText){ return Utilities.textWait(driver, this.tableClaimedStatusText, claimedText, 10);}

    public void tableClaimedStatusButtonClick() { Utilities.clickableWait(driver, this.tableClaimedStatusButton, 10).click();}

    public boolean tableOccupiedTextPresent(String claimedText){ return Utilities.textWait(driver, this.tableOccupiedStatusText, claimedText, 10);}

    public void tableOccupiedStatusButtonClick() { Utilities.clickableWait(driver, this.tableOccupiedStatusButton, 10).click();}

    public boolean activeOrdersTitlePresent(){ return Utilities.textWait(driver, this.activeOrdersTitle, "Active orders", 10);}

    public void viewActiveOrderButtonClicked(int tableRow) { Utilities.clickableWait(driver, this.viewActiveOrderButtons.get(tableRow-1), 10).click();   }

    public void editActiveOrderButtonClicked(int rowNumber) { Utilities.clickableWait(driver, this.editActiveOrderButtons.get(rowNumber-1), 10).click();   }

    public void createOrderButtonClick() { Utilities.clickableWait(driver, this.createOrderButton, 10).click();}

    public void closeTableOptionsButtonClick() { Utilities.clickableWait(driver, this.closeTableOptionsButton, 10).click();}

    public boolean activeOrdersPresent(int numberOfOrders){
        return this.viewActiveOrderButtons.size() == numberOfOrders;
    }

    public void waitUntilOrderPresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@name='order-number-text'][1]"), 10);
    }
}
