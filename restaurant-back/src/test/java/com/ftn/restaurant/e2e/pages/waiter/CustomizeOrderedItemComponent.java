package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    @FindBy(xpath = "//*[@id=\"form-holder-div\"]/div[2]/mat-selection-list/mat-list-option")
    private List<WebElement> activeIngredientsOptions;

    @FindBy(xpath = "//*[@id=\"quantity-input\"]")//
    private WebElement quantityInput;

    @FindBy(xpath = "//*[@id=\"priority-options\"]")
    private List<WebElement> priorityOptions;

    @FindBy(xpath = "//*[@id=\"mat-select-0\"]")
    private WebElement prioritySelect;

    @FindBy(xpath = "//*[@id=\"menu-item-price\"]")
    private WebElement menuItemPrice;

    public CustomizeOrderedItemComponent(WebDriver driver){ this.driver = driver;}

    public boolean menuItemNamePresent(String name){ return Utilities.textWait(driver, this.menuItemName, "Customize " + name, 10);}

    public void cancelCustomizedOrderedItemButtonClicked() { Utilities.clickableWait(driver, this.cancelCustomizedOrderedItemButton, 10).click();   }

    public void addCustomizedOrderedItemButtonClicked() { Utilities.clickableWait(driver, this.addCustomizedOrderedItemButton, 10).click();   }

    public void saveChangesToCustomizedOrderedItemButtonClicked() { Utilities.clickableWait(driver, this.saveChangesToCustomizedOrderedItemButton, 10).click();   }

    public void deselectActiveIngredient(int rowNumber){Utilities.clickableWait(driver, this.activeIngredientsOptions.get(rowNumber-1), 10).click(); }

    public void prioritySelectClick(){Utilities.clickableWait(driver, this.prioritySelect, 10).click(); }

    public void selectPriorityOption(int rowNumber){Utilities.clickableWait(driver, this.priorityOptions.get(rowNumber-1), 10).click(); }

    public WebElement getQuantityInput() {
        return Utilities.visibilityWait(driver, this.quantityInput, 10);
    }

    public boolean menuItemPricePresent(int price){
        String vl = this.menuItemPrice.getText();
        return vl.equals("$ " + price);
    }


    public void setQuantityInput(int value) {
        WebElement we = getQuantityInput();
        we.clear();
        we.sendKeys(String.valueOf(value));
    }

    public void waitUntilIngredientsPresent(){
        Utilities.visibilityWaitByLocator(driver, By.xpath("//*[@id=\"form-holder-div\"]/div[2]/mat-selection-list/mat-list-option[1]"), 10);
    }
}
