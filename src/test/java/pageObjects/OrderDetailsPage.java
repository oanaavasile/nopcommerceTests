package pageObjects;

import model.BillingDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderDetailsPage {
    protected WebDriver driver;
    public static final By billingName = By.cssSelector(".billing-info .info-list .name");
    public static final By billingEmail = By.cssSelector(".billing-info .info-list .email");
    public static final By billingPhone = By.cssSelector(".billing-info .info-list .phone");
    public static final By billingFax = By.cssSelector(".billing-info .info-list .fax");
    public static final By billingCountry = By.cssSelector(".billing-info .info-list .country");
    public static final By billingState = By.cssSelector(".billing-info .info-list .stateprovince");
    public static final By billingCity = By.cssSelector(".billing-info .info-list .city");
    public static final By billingAddress1 = By.cssSelector(".billing-info .info-list .address1");
    public static final By billingAddress2 = By.cssSelector(".billing-info .info-list .address2");
    public static final By billingPostalCode = By.cssSelector(".billing-info .info-list .zippostalcode");
    public OrderDetailsPage(WebDriver driver) {
        this.driver = driver;
    }
    public BillingDetails getBillingDetails() {

        String fullName = driver.findElement(billingName).getText();
        String[] nameParts = fullName.split(" ", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        String emailString = driver.findElement(billingEmail).getText();
        String email = emailString.replace("Email:","").trim();
        String phoneString = driver.findElement(billingPhone).getText();
        String phoneNumber = phoneString.replace("Phone:","").trim();
        String faxNumber = driver.findElement(billingFax).getText();
        String country = driver.findElement(billingCountry).getText();
        String state = driver.findElement(billingState).getText();
        String city = driver.findElement(billingCity).getText();
        String address1 = driver.findElement(billingAddress1).getText();
        String address2 = driver.findElement(billingAddress2).getText();
        String postalCode = driver.findElement(billingPostalCode).getText();

        // Create a new BillingDetails object
        BillingDetails billingDetails = new BillingDetails(
                firstName, lastName, email, country, state, city, address1, postalCode, phoneNumber
        );

        // Set optional fields
        if (address2 != null && !address2.isEmpty()) {
            billingDetails.setAddress2(address2);
        }

        if (faxNumber != null && !faxNumber.equals("Fax:")) {
            if(faxNumber.startsWith("Fax:")){
                faxNumber = faxNumber.replace("Fax:", "");
            }
            billingDetails.setFaxNumber(faxNumber.trim());
        }

        // Return the populated BillingDetails object
        return billingDetails;
    }
}
