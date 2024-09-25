package pageObjects;

import model.BillingDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
    protected WebDriver driver;
    public static final By shipToSameAddressCheckbox = By.id("ShipToSameAddress");
    public static final By billingNewFirstName = By.id("BillingNewAddress_FirstName");
    public static final By billingNewLastName = By.id("BillingNewAddress_LastName");
    public static final By email = By.id("BillingNewAddress_Email");
    public static final By country = By.id("BillingNewAddress_CountryId");
    public static final By state = By.id("BillingNewAddress_StateProvinceId");
    public static final By city = By.id("BillingNewAddress_City");
    public static final By address = By.id("BillingNewAddress_Address1");
    public static final By postalCode = By.id("BillingNewAddress_ZipPostalCode");
    public static final By phoneNumber = By.id("BillingNewAddress_PhoneNumber");
    public static final By billingAddress = By.id("billing-address-select");
    public static final By continueButtonBillingAddress = By.cssSelector("button.button-1.new-address-next-step-button");
    public static final By continueButtonShipping = By.cssSelector("button.button-1.shipping-method-next-step-button");
    public static final By continueButtonPayment = By.cssSelector("button.button-1.payment-method-next-step-button");
    public static final By continueButtonPaymentInfo = By.cssSelector("button.button-1.payment-info-next-step-button");
    public static final By confirmOrderButton = By.cssSelector("button.button-1.confirm-order-next-step-button");
    public static final By successfulMessage = By.cssSelector(".section.order-completed .title");


    public CheckoutPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean checkIfShipToSameAddressIsChecked(){
        boolean isChecked = driver.findElement(shipToSameAddressCheckbox).getAttribute("value").equals("true");

        return isChecked;
    }

    public void completeRequiredBilling(BillingDetails billingDetails, WebDriverWait wait){
        Select billingAddressDropdown = new Select(driver.findElement(billingAddress));
        billingAddressDropdown.selectByVisibleText("New Address");

        wait.until(ExpectedConditions.visibilityOfElementLocated(billingNewFirstName));

        driver.findElement(billingNewFirstName).sendKeys(billingDetails.getFirstName());
        driver.findElement(billingNewLastName).sendKeys(billingDetails.getLastName());
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(billingDetails.getEmail());
        Select selectCountry = new Select(driver.findElement(country));
        selectCountry.selectByVisibleText(billingDetails.getCountry());
        wait.until(ExpectedConditions.visibilityOfElementLocated(state));
        Select selectState = new Select(driver.findElement(state));
        selectState.selectByVisibleText(billingDetails.getState());
        driver.findElement(city).sendKeys(billingDetails.getCity());
        driver.findElement(address).sendKeys(billingDetails.getAddress());
        driver.findElement(postalCode).sendKeys(billingDetails.getPostalCode());
        driver.findElement(phoneNumber).sendKeys(billingDetails.getPhoneNumber());
    }

    public void clickContinueBillingAddress(){
        driver.findElement(continueButtonBillingAddress).click();
    }
    public void clickContinueShipping(WebDriverWait wait){
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButtonShipping)).click();
    }

    public void clickContinuePayment(WebDriverWait wait){
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButtonPayment)).click();
    }

    public void clickContinuePaymentInfo(WebDriverWait wait){
        wait.until(ExpectedConditions.visibilityOfElementLocated(continueButtonPaymentInfo)).click();
    }

    public void clickConfirmOrder(WebDriverWait wait){
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrderButton)).click();
    }

    public String getOrderNumber(){
        String orderStr = driver.findElement(By.className("order-number")).getText();
        return orderStr.substring(orderStr.lastIndexOf(" ")).trim();
    }

    public void checkShipToSameAddressCheckbox(){
        driver.findElement(shipToSameAddressCheckbox).click();
    }

    public String getSuccessfulMessage(){
        return driver.findElement(successfulMessage).getText();
    }

    public void completeAllSteps(BillingDetails billingDetails, WebDriverWait wait){
        completeRequiredBilling(billingDetails,wait);
        clickContinueBillingAddress();
        clickContinueShipping(wait);
        clickContinuePayment(wait);
        clickContinuePaymentInfo(wait);
        clickConfirmOrder(wait);
    }


}
