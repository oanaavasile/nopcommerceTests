package utils;

import constants.Urls;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.List;


@Slf4j
public class NavigationUtils {
    public static final By headerMenu = By.className("header-menu");
    public static final By electronicsSection = By.linkText("Electronics");
    public static final By cellPhonesSection = By.partialLinkText("Cell phones");
    public static final By shoesSection = By.partialLinkText("Shoes");
    public static final By apparelSection = By.linkText("Apparel");
    public static final By cartButton = By.cssSelector("span.cart-label");
    public static final By closePopupButton = By.cssSelector("#bar-notification .close");
    public static final By popup = By.id("bar-notification");
    public static final By cameraSection = By.partialLinkText("Camera & photo");
    public static final By currentItemBreadcrumb = By.cssSelector(".current-item");
    public static final By errorBar = By.cssSelector(".bar-notification.error");
    public static final By closeErrorBar = By.cssSelector(".bar-notification.error .close");
    public static final By myAccountMenu = By.className("ico-account");
    public static final By ordersMenu = By.cssSelector("li.customer-orders > a");


    public static void goToElectronicsSection(WebDriver driver) {
        driver.findElement(electronicsSection).click();
    }

    public static void goToApparelSection(WebDriver driver) {
        driver.findElement(apparelSection).click();
    }

    public static void goToCellPhones(WebDriver driver) {
        driver.findElement(cellPhonesSection).click();
    }

    public static void goToCameraAndPhoto(WebDriver driver) {
        driver.findElement(cameraSection).click();
    }

    public static void goToShoesSection(WebDriver driver) {
        driver.findElement(shoesSection).click();
    }

    public static void goToApparelShoes(WebDriver driver){
    goToApparelSection(driver);
    goToShoesSection(driver);

    }

    public void goToCategory(WebDriver driver, String category) {
        WebElement headerMenuElement = driver.findElement(headerMenu);
        headerMenuElement.findElement(By.linkText(category)).click();
    }

    public void waitForClosePopUpButton(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(closePopupButton));
    }

    public static void goToShoppingCart(WebDriverWait wait, WebDriver driver) {

        // Wait for the cart button to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));

        // Click the cart button
        driver.findElement(cartButton).click();
    }

    public static void closeOverlayingPopUp(WebDriverWait wait, WebDriver driver) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(popup));

        try {
            // Check if the popup is present
            if (driver.findElements(popup).size() > 0) {
                // Close the popup if it's present
                driver.findElement(closePopupButton).click();
                log.info("Popup closed manually.");

                //Wait for the popup to be gone before proceeding
                wait.until(ExpectedConditions.invisibilityOfElementLocated(popup));
            }
        } catch (org.openqa.selenium.WebDriverException e) {
            log.warn("Popup not found while trying to close it. Skipping...");
        }
    }

    public static void waitForErrorBarToBeVisible(WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorBar));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error bar not visible");
        }
    }

    public static void closeErrorBar(WebDriver driver) {
        driver.findElement(closeErrorBar).click();
    }

    public static void goToOrders(WebDriver driver, WebDriverWait wait){
        driver.findElement(myAccountMenu).click();
        wait.until(ExpectedConditions.urlToBe(Urls.BASE_URL+ Urls.MY_ACCOUNT_PATH));
        driver.findElement(ordersMenu).click();
        wait.until(ExpectedConditions.urlToBe(Urls.BASE_URL+ Urls.ORDER_HISTORY_PATH));
    }


}
