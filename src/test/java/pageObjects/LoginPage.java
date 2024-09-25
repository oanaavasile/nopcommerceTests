package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    protected WebDriver driver;

    public static final By username = By.id("Email");
    public static final By password = By.id("Password");

    public static final By loginButton = By.cssSelector("button.button-1.login-button");

    public static final By errorMessage = By.cssSelector(".message-error.validation-summary-errors");
    public static final By emailError = By.id("Email-error");

    public static final By logoutButton = By.cssSelector(".ico-logout");
    public static final By myAccountButton = By.cssSelector(".ico-account");

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void enterUsername (String username){
        driver.findElement(LoginPage.username).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(this.password).sendKeys(password);
    }

    public void clickLogin(){
        driver.findElement(this.loginButton).click();
    }
    public void login(String username, String password){
        if(!username.isEmpty()){
            driver.findElement(this.username).sendKeys(username);
        }
        if(!password.isEmpty()) {
            driver.findElement(this.password).sendKeys(password);
        }
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

    public String getEmailError(){
        return driver.findElement(emailError).getText();
    }

    public void logout(){
        driver.findElement(logoutButton).click();
    }
}

