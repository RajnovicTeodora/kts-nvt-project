package com.ftn.restaurant.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

    private WebDriver driver;

    public static final String URL = "http://localhost:4200/login";

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement passwordInput;

    @FindBy(id = "submit-button")
    private WebElement loginButton;

    @FindBy(xpath = "//*[@id='login-button']")
    private WebElement loginBtnForOtherAccounts;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameInput() {
        return Utilities.visibilityWait(driver, this.usernameInput, 10);
    }

    public void setUsernameInput(String text) {
        WebElement we = getUsernameInput();
        we.clear();
        we.sendKeys(text);
    }

    public WebElement getPasswordInput() {
        return Utilities.visibilityWait(driver, this.passwordInput, 40);
    }

    public void setPasswordInput(String text) {
        WebElement we = getPasswordInput();
        we.clear();
        we.sendKeys(text);
    }

    public void loginBtnClick(){
        Utilities.clickableWait(driver, this.loginButton, 10).click();
    }

    public void loginBtnForOtherAccountsClick(){
        Utilities.clickableWait(driver, this.loginBtnForOtherAccounts, 10).click();
    }

    public boolean urlPresent() {
        return Utilities.urlWait(driver, URL, 10);
    }

    public void login(String username, String password) {
        this.setUsernameInput(username);
        this.setPasswordInput(password);
        this.loginBtnClick();
    }
}
