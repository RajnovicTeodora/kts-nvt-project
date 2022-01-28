package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AddDrinkPage {

    private WebDriver driver;

    public static final String URL = "http://localhost:4200/login";

    @FindBy(id = "name-field")
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

    @FindBy(id = "submit-drink-button")
    private WebElement submitDrinkButton;

    public AddDrinkPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getNameInput() {
        return Utilities.visibilityWait(driver, this.nameInput, 10);
    }

    public void setNameInput(String text) {
        WebElement we = getNameInput();
        we.clear();
        we.sendKeys(text);
    }

    public void setDrinkType() {
        Utilities.clickableWait(driver, this.drinkType, 10).click();
        Utilities.clickableWait(driver, this.drinkTypeOption, 10).click();
    }

    public void setDrinkContainer() {
        Utilities.clickableWait(driver, this.drinkContainer, 10).click();
        Utilities.clickableWait(driver, this.drinkContainerOption, 10).click();
    }

    public WebElement getFileInput() {
        return Utilities.visibilityWait(driver, this.fileInput, 10);
    }

    public void setFileInput(String text) {
        WebElement we = getFileInput();
        we.clear();
        we.sendKeys(text);
    }

    public void submitDrinkBtnClick() {
        Utilities.clickableWait(driver, this.submitDrinkButton, 10).click();
    }

}
