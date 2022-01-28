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

    public CreateOrderPage(WebDriver driver){ this.driver = driver;}

    public void cancelCreateOrderButtonClick() { Utilities.clickableWait(driver, this.cancelCreateOrderButton, 10).click();}

    public boolean urlPresent(int tableNumber) {
        return Utilities.urlWait(driver, URL + "/" + tableNumber, 10);
    }
}
