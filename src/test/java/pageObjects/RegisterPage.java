package pageObjects;

import model.RandomRegistrationData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    WebDriver driver;
    WebDriverWait wait;
    private static final By maleRadioBtn = By.id("gender-male");
    private static final By femaleRadioBtn = By.id("gender-female");
    private static final By firstNameInput = By.id("FirstName");
    private static final By lastNameInput = By.id("LastName");
    private static final By dayOfBirthDropdown = By.cssSelector("select[name='DateOfBirthDay']");
    private static final By monthOfBirthDropdown = By.cssSelector("select[name='DateOfBirthMonth']");
    private static final By yearOfBirthDropdown = By.cssSelector("select[name='DateOfBirthYear']");
    private static final By emailInput = By.id("Email");
    private static final By companyNameInput = By.id("Company");
    private static final By passwordInput = By.id("Password");
    private static final By confirmPassInput = By.id("ConfirmPassword");
    private static final By registerBtn = By.id("register-button");
    private static final By registrationMessage = By.cssSelector("#main .result");
    private static final By myAccountIcon = By.cssSelector(".header-links .ico-account");
    private static final By firstNameErrorLocator = By.cssSelector("#FirstName ~ .field-validation-error");
    private static final By lastNameErrorLocator = By.cssSelector("#LastName ~ .field-validation-error");
    private static final By emailErrorLocator = By.cssSelector("input[name='Email'] ~ .field-validation-error");
    private static final By passErrorLocator = By.cssSelector("#ConfirmPassword ~ .field-validation-error");
    private static final By emailExistsLocator = By.cssSelector(".message-error.validation-summary-errors");

    public RegisterPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void completePersonalDetails(RandomRegistrationData data) {

        if (data.getGender() == "Female") {
            driver.findElement(femaleRadioBtn).click();
        } else if (data.getGender() == "Male") {
            driver.findElement(maleRadioBtn).click();
        }

        driver.findElement(firstNameInput).sendKeys(data.getFirstName());
        driver.findElement(lastNameInput).sendKeys(data.getLastName());

//        Select dayOfBirth = new Select(driver.findElement(dayOfBirthDropdown));
//        dayOfBirth.selectByVisibleText(data.getDay());
//
//        Select monthOfBirth = new Select(driver.findElement(monthOfBirthDropdown));
//        monthOfBirth.selectByVisibleText(data.getMonth());
//
//        Select yearOfBirth = new Select(driver.findElement(yearOfBirthDropdown));
//        yearOfBirth.selectByVisibleText(data.getYear());

        completeEmail(data.getEmail());
    }

    public void completeEmail(String email){
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void completeCompanyDetails(String companyName) {
        driver.findElement(companyNameInput).sendKeys(companyName);
    }

    public void completePassword(String password, String confirmPass) {
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(confirmPassInput).sendKeys(confirmPass);
    }

    public void completeAll(RandomRegistrationData data){
        completePersonalDetails(data);
        completeCompanyDetails(data.getCompanyName());
        completePassword(data.getPassword(),data.getPassword());
    }



    public void clickRegister() {
        driver.findElement(registerBtn).click();
    }

    public String getRegistrationMessage() {
        return driver.findElement(registrationMessage).getText();
    }

    public boolean isMyAccountIconVisible() {
        return driver.findElement(myAccountIcon).isDisplayed();
    }

    public String getFirstNameErrorTextIfVisible() {
        try {
            WebElement firstNameErrorElement = driver.findElement(firstNameErrorLocator);
            if (firstNameErrorElement.isDisplayed()) {
                return firstNameErrorElement.getText();
            } else {
                // Handle the case where the element is found but not visible
                return null;
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the element is not found
            return null;
        } catch (StaleElementReferenceException e) {
            // Handle the case where the element becomes stale
            return null;
        }
    }

    public String getLastNameErrorTextIfVisible() {
        try {
            WebElement lastNameErrorElement = driver.findElement(lastNameErrorLocator);
            if (lastNameErrorElement.isDisplayed()) {
                return lastNameErrorElement.getText();
            } else {
                // Handle the case where the element is found but not visible
                return null;
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the element is not found
            return null;
        } catch (StaleElementReferenceException e) {
            // Handle the case where the element becomes stale
            return null;
        }
    }

    public String getEmailErrorTextIfVisible() {
        try {
            WebElement emailErrorText = driver.findElement(emailErrorLocator);
            if (emailErrorText.isDisplayed()) {
                return emailErrorText.getText();
            } else {
                // Handle the case where the element is found but not visible
                return null;
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the element is not found
            return null;
        } catch (StaleElementReferenceException e) {
            // Handle the case where the element becomes stale
            return null;
        }
    }

    public String getPassErrorTextIfVisible() {
        try {
            WebElement passErrorText = driver.findElement(passErrorLocator);
            if (passErrorText.isDisplayed()) {
                return passErrorText.getText();
            } else {
                // Handle the case where the element is found but not visible
                return null;
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the element is not found
            return null;
        } catch (StaleElementReferenceException e) {
            // Handle the case where the element becomes stale
            return null;
        }
    }

    public String getEmailExistsTextIfVisible() {
        try {
            WebElement emailExistsText = driver.findElement(emailExistsLocator);
            if (emailExistsText.isDisplayed()) {
                return emailExistsText.getText();
            } else {
                // Handle the case where the element is found but not visible
                return null;
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the element is not found
            return null;
        } catch (StaleElementReferenceException e) {
            // Handle the case where the element becomes stale
            return null;
        }
    }

    public By getFirstNameErrorLocator(){
        return firstNameErrorLocator;
    }

    public By getLastNameErrorLocator(){
        return lastNameErrorLocator;
    }

    public By getEmailErrorLocator(){
        return emailErrorLocator;
    }

    public By getEmailExistsLocator(){
        return emailExistsLocator;
    }

    public By getPassErrorLocator(){
        return passErrorLocator;
    }


}
