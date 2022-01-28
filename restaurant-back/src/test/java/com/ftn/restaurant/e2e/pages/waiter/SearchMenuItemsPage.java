package com.ftn.restaurant.e2e.pages.waiter;

import com.ftn.restaurant.e2e.pages.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchMenuItemsPage {

    private WebDriver driver;

    @FindBy(name="drink")
    private WebElement drinkBttn;

    @FindBy(name="dish")
    private WebElement dishBttn;

    @FindBy(className = "searchInput")
    private WebElement searchInput;

    @FindBy(name = "searchBttn")
    private WebElement searchBttn;

    @FindBy(className = "dugmic-back")
    private WebElement backBttn;

    @FindBy(id = "create-order-button")
    private WebElement newOrderBttn;


    public SearchMenuItemsPage(WebDriver driver){
        this.driver = driver;
    }

    private WebElement getSearchField(){
        return Utilities.visibilityWait(driver, this.searchInput, 10);
    }

    public void insertSearchText(String text){
        WebElement el = getSearchField();
        el.clear();
        el.sendKeys(text);
    }

    public void clickSearch(){
        Utilities.clickableWait(driver, this.searchBttn, 10).click();
    }

    public void clickNewOrder(){
        Utilities.clickableWait(driver, this.newOrderBttn, 10).click();
    }

    public boolean confirmSearchSuccess(String text){
        List<WebElement> searchResults = Utilities.visibilityWait(driver, By.name("menuItem"), 10);
        for(WebElement element : searchResults){
            if(!element.getText().toLowerCase().contains(text.toLowerCase())){
                return false;
            }
        }
        return true;
    }

    public void backClick(){
        Utilities.clickableWait(driver, this.backBttn, 10);
    }

    public void drinkClick(){
        Utilities.clickableWait(driver, this.drinkBttn, 20).click();
    }

    public boolean confirmDrinkCategories(){
        List<String> types = new ArrayList<String>();
        types.add("Coffee");
        types.add("Hot drink");
        types.add("Alcoholic");
        types.add("Cold drink");
        List<WebElement> drinkCategories = Utilities.visibilityWait(driver, By.name("drinkType"), 20);
        for (WebElement cat : drinkCategories){
            if(!types.contains(cat.getText())) return false;
        }
        return true;
    }

    public void dishClick(){
        Utilities.clickableWait(driver, this.dishBttn, 20).click();
    }

    public void desertClick(){
        List<WebElement> dishTypes = Utilities.visibilityWait(driver, By.name("dishType"), 20);
        for (WebElement dishType : dishTypes){
            if (dishType.getText().equals("Desert")){
                Utilities.clickableWait(driver, dishType, 10).click();
                return;
            }
        }
    }

    public boolean confirmDishes(){
        List<WebElement> dishes = Utilities.visibilityWait(driver, By.name("menuItem"), 10);
        for (WebElement dish : dishes){
            if(dish.getText().equals("Pancakes")) return true;
        }
        return false;
    }


}
