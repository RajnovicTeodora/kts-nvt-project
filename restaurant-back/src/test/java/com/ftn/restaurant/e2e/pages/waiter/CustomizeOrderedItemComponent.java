package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CustomizeOrderedItemComponent {
    private WebDriver driver;

    @FindBy(id = "menu-item-name-title")
    private WebElement menuItemName;

    @FindBy(id = "cancel-customized-ordered-item-button")
    private WebElement cancelCustomizedOrderedItemButton;

    @FindBy(id = "add-customized-ordered-item-button")
    private WebElement addCustomizedOrderedItemButton;

    @FindBy(id = "save-changes-to-customized-ordered-item-button")
    private WebElement saveChangesToCustomizedOrderedItemButton;

    public CustomizeOrderedItemComponent(WebDriver driver){ this.driver = driver;}

    public boolean menuItemNamePresent(String name){ return Utilities.textWait(driver, this.menuItemName, name, 10);}

    public void cancelCustomizedOrderedItemButtonClicked() { Utilities.clickableWait(driver, this.cancelCustomizedOrderedItemButton, 10).click();   }

    public void addCustomizedOrderedItemButtonClicked() { Utilities.clickableWait(driver, this.addCustomizedOrderedItemButton, 10).click();   }

    public void saveChangesToCustomizedOrderedItemButtonClicked() { Utilities.clickableWait(driver, this.saveChangesToCustomizedOrderedItemButton, 10).click();   }

}
