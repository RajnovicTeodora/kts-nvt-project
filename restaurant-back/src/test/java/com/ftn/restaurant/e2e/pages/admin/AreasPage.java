package com.ftn.restaurant.e2e.pages.admin;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AreasPage {

    private WebDriver driver;

    @FindBy(name = "dugmic-area")
    private List<WebElement> areaBttns;

    @FindBy(className = "btn-primary")
    private WebElement confirmBttn;

    @FindBy(name = "addArea")
    private WebElement addAreaBttn;

    @FindBy(name = "return")
    private WebElement returnBttn;

    @FindBy(name = "addTableBttn")
    private WebElement addTableBttn;

    @FindBy(name = "saveTableBttn")
    private WebElement saveChangesBttn;

    @FindBy(name = "deleteTableBttn")
    private WebElement deleteTableBttn;

    @FindBy(id = "name")
    private WebElement newAreaInput;

    @FindBy(id = "save-button")
    private WebElement saveNewAreaBttn;

    @FindBy(id = "tableBttn")
    private List<WebElement> tableBttns;

    public AreasPage(WebDriver driver){
        this.driver = driver;
    }

    public void clickOnArea(int areaNum){
        Utilities.clickableWait(driver, this.areaBttns.get(areaNum), 10).click();
    }

    public void deleteAreaClick(int areaNum){
        List<WebElement> deleteBttns = Utilities.visibilityWait(driver, By.name("delete-area-button"), 10);
        Utilities.clickableWait(driver, deleteBttns.get(areaNum), 10).click();
    }

    public void confirmDeleteClicked(){
        Utilities.clickableWait(driver, this.confirmBttn, 10).click();
    }

    public void addAreaClick(){
        Utilities.clickableWait(driver, this.addAreaBttn, 15).click();
    }

    public void returnClick(){
        Utilities.clickableWait(driver, this.returnBttn, 10).click();
    }

    public void addTableClick(){
        Utilities.clickableWait(driver, this.addTableBttn, 10).click();
    }

    public void saveChangesClick(){
        Utilities.clickableWait(driver, this.saveChangesBttn, 10).click();
    }

    public void deleteTableClick(){
        Utilities.clickableWait(driver, this.deleteTableBttn, 10).click();
    }

    public void saveNewAreaClick() {
        Utilities.clickableWait(driver, this.saveNewAreaBttn, 10).click();
    }

    private WebElement getInputField(){
        WebElement inputField = driver.findElement(By.id("name"));
        return Utilities.visibilityWait(driver, this.newAreaInput, 10);
    }

    public void setNewAreaName(String name){
        WebElement el = getInputField();
        el.clear();
        el.sendKeys(name);
    }

    public void doubleClickTable(int tableNum){
       for(WebElement table : this.tableBttns){
           if(table.getText().equals(String.valueOf(tableNum))){
               Actions actions = new Actions(driver);
               actions.moveToElement(table).doubleClick().perform();
               break;
           }
       }
    }

    public int countTables(){
        return Utilities.visibilityWait(driver, By.id("tableBttn"), 10).size();
    }

    public int countAreas(){
        return Utilities.visibilityWait(driver, By.name("dugmic-area"), 30).size();
    }

    public boolean checkAreaExists(String name){
        List<WebElement> areas = Utilities.visibilityWait(driver, By.name("dugmic-area"), 20);
        for(WebElement area : areas){
            if(area.getText().equals(name)){
                return true;
            }
        }
        return false;
    }
}
