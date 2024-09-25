package tests;

import constants.RegistrationMessages;
import constants.Urls;
import model.RandomRegistrationData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.RegisterPage;

import java.time.Duration;

public class RegistrationTests extends BaseTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private RegisterPage registerPage;
    private String newEmail;

    @BeforeMethod
    public void setup() {
        driver = browserSetup();
        wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        registerPage = new RegisterPage(driver, wait);
    }

    @AfterClass
    public void afterTest() {
        BaseTest.tearDown(driver);
    }

    @Test
    public void validRegistration() {
        RandomRegistrationData randomRegistrationData = new RandomRegistrationData();
        randomRegistrationData.generateRandomAll();

        driver.get(Urls.BASE_URL+ Urls.REGISTER_PATH);
        registerPage.completeAll(randomRegistrationData);
        registerPage.clickRegister();

        Assert.assertEquals(registerPage.getRegistrationMessage(), RegistrationMessages.REGISTRATION_SUCCESS);
        Assert.assertTrue(registerPage.isMyAccountIconVisible(), "My Account icon is not visible.");

        newEmail = randomRegistrationData.getEmail();
    }

    @Test(dependsOnMethods = {"validRegistration"})
    public void existingEmail() {
        RandomRegistrationData randomRegistrationData = new RandomRegistrationData();
        randomRegistrationData.generateRandomAll();

        driver.get(Urls.BASE_URL+ Urls.REGISTER_PATH);
        registerPage.completeAll(randomRegistrationData);

//        insert existing email
        registerPage.completeEmail(newEmail);
        registerPage.clickRegister();

        wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getEmailExistsLocator()));

        String emailExistsError = registerPage.getEmailExistsTextIfVisible();
        if (emailExistsError != null) {
            Assert.assertEquals(emailExistsError, RegistrationMessages.EMAIL_EXISTS);
        } else {
            Assert.fail("Different pass error not visible");
        }

    }

    @Test
    public void missingMandatoryFields() throws InterruptedException {
        driver.get(Urls.BASE_URL+ Urls.REGISTER_PATH);
        registerPage.clickRegister();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getFirstNameErrorLocator()));
        SoftAssert softAssert = new SoftAssert();

        String firstNameError = registerPage.getFirstNameErrorTextIfVisible();
        if (firstNameError != null) {
            softAssert.assertEquals(firstNameError, RegistrationMessages.FIRST_NAME_REQUIRED);
        } else {
            softAssert.fail("First name error message is not visible.");
        }

        String lastNameError = registerPage.getLastNameErrorTextIfVisible();
        if (lastNameError != null) {
            softAssert.assertEquals(lastNameError, RegistrationMessages.LAST_NAME_REQUIRED);
        } else {
            softAssert.fail("Last name error message is not visible.");
        }

        String emailError = registerPage.getEmailErrorTextIfVisible();
        if (emailError != null) {
            softAssert.assertEquals(emailError, RegistrationMessages.EMAIL_REQUIRED);
        } else {
            softAssert.fail("Email error message is not visible.");
        }

        String passError = registerPage.getPassErrorTextIfVisible();
        if (passError != null) {
            Assert.assertEquals(passError, RegistrationMessages.PASSWORD_REQUIRED);
        } else {
            Assert.fail("Pass error message is not visible.");
        }

        softAssert.assertAll();
    }

    @Test
    public void invalidEmailFormat() {
        RandomRegistrationData randomRegistrationData = new RandomRegistrationData();
        randomRegistrationData.generateRandomAll();

        driver.get(Urls.BASE_URL+ Urls.REGISTER_PATH);
        registerPage.completeAll(randomRegistrationData);

//      insert invalid email
        registerPage.completeEmail("dd");
        registerPage.clickRegister();

        wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getEmailErrorLocator()));

        String emailError = registerPage.getEmailErrorTextIfVisible();
        if (emailError != null) {
            Assert.assertEquals(emailError, RegistrationMessages.INVALID_EMAIL_ERROR);
        } else {
            Assert.fail("Email not valid error not visible");
        }
    }

    @Test
    public void differentConfirmPass() {
        RandomRegistrationData randomRegistrationData = new RandomRegistrationData();
        randomRegistrationData.generateRandomAll();

        driver.get(Urls.BASE_URL+ Urls.REGISTER_PATH);
        registerPage.completePersonalDetails(randomRegistrationData);
        registerPage.completeCompanyDetails(randomRegistrationData.getCompanyName());

//        insert different confirm pass
        registerPage.completePassword(randomRegistrationData.getPassword(),"asd123");
        registerPage.clickRegister();

        wait.until(ExpectedConditions.visibilityOfElementLocated(registerPage.getPassErrorLocator()));

        String passError = registerPage.getPassErrorTextIfVisible();
        if (passError != null) {
            Assert.assertEquals(passError, RegistrationMessages.DIFFERENT_PASSWORDS);
        } else {
            Assert.fail("Different pass error not visible");
        }


    }



}
