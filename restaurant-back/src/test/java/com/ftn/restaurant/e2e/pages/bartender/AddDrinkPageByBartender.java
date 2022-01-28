package com.ftn.restaurant.e2e.pages.bartender;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddDrinkPageByBartender {
    private WebDriver driver;

    public static final String URL = "http://localhost:4200/bartender-dashboard";

    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "drink-type-select")
    private WebElement drinkType;

    @FindBy(xpath = "//span[contains(text(),'Coffee')]")
    private WebElement drinkTypeOption;

    @FindBy(id = "drink-container-select")
    private WebElement drinkContainer;

    @FindBy(xpath = "//span[contains(text(),'Glass')]")
    private WebElement drinkContainerOption;

    @FindBy(id = "file-upload")
    private WebElement fileInput;

    @FindBy(id = "submit-button")
    private WebElement submitDrinkButton;

    @FindBy(id = "isSaved")
    private WebElement message;

    @FindBy(xpath = "//*[@class='toast-top-right toast-container']/div[2]")
    private WebElement message2;

    @FindBy(id = "input-ingredient")
    private WebElement inputIngredientBtn;

    @FindBy(id = "rowTest")
    private WebElement rowTableIngredient;

    @FindBy(id = "ing-name")
    private WebElement nameIngredient;

    @FindBy(id = "isAlergenY")
    private WebElement radioBtn;

    @FindBy(id = "save-ing")
    private WebElement saveIngredientBtn;

    public AddDrinkPageByBartender(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNameInput() {
        return Utilities.visibilityWait(driver, this.nameInput, 40);
    }

    public void setNameInput(String text) {
        WebElement we = getNameInput();
        we.clear();
        we.sendKeys(text);
    }
    public void setNameIngredient(String text) {
        WebElement we = getNameIngredient();
        we.clear();
        we.sendKeys(text);
    }

    public void setDrinkType() {
        Utilities.clickableWait(driver, this.drinkType, 40).click();
        Utilities.clickableWait(driver, this.drinkTypeOption, 40).click();
    }

    public void setDrinkContainer() {
        Utilities.clickableWait(driver, this.drinkContainer, 40).click();
        Utilities.clickableWait(driver, this.drinkContainerOption, 40).click();
    }

    public WebElement getFileInput() {
        return Utilities.visibilityWait(driver, this.fileInput, 40);
    }

    public WebElement getNameIngredient() {
        return Utilities.visibilityWait(driver, this.nameIngredient, 40);
    }
    public WebElement getRowTableIngredient() {
        return Utilities.visibilityWait(driver, this.rowTableIngredient, 40);
    }

    public void setFileInput(String text) {
        WebElement we = getFileInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitDrinkBtnClick() {
        Utilities.clickableWait(driver, this.submitDrinkButton, 40).click();
    }

    public WebElement getMessage(){ return Utilities.visibilityWait(driver, this.message, 40);}
    public WebElement getMessage2(){ return Utilities.visibilityWait(driver, this.message2, 40);}

    public void inputIngredientBtnClick() {
        Utilities.clickableWait(driver, this.inputIngredientBtn, 40).click();
    }
    public void radioBtnClick() {
        Utilities.clickableWait(driver, this.radioBtn, 40).click();
    }
    public void saveIngredientBtnClick() {
        Utilities.clickableWait(driver, this.saveIngredientBtn, 40).click();
    }
}
