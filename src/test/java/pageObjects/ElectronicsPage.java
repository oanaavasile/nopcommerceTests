package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ElectronicsPage {
    protected WebDriver driver;
    public static final String productPictureLocator = ".product-item .picture";
    public static final By addToCartButton = By.className("add-to-cart-button");
    public static final By closePopupButton = By.cssSelector(".close");
    public static final By successPopUp = By.cssSelector(".bar-notification.success");
    public static final By addToCartButtonListOfProducts = By.cssSelector(".button-2.product-box-add-to-cart-button");
    public static final By productPrice = By.cssSelector(".product-price span");
    public static final By productName = By.cssSelector(".product-name h1");
    public static final By reviewList = By.cssSelector(".product-review-list");

    public ElectronicsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToProduct(int productIndex) {
        List<WebElement> productsOnListPage = driver.findElements(By.cssSelector(productPictureLocator));
        WebElement productRequired = productsOnListPage.get(productIndex);
        productRequired.click();
    }

    public String getPrice() {
        return driver.findElement(productPrice).getText().replace("$","");
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();

        try {
            WebElement successPopUpElement = driver.findElement(successPopUp);
            if (successPopUpElement != null) {
                // Now interact with the close button
                driver.findElement(closePopupButton).click();
            }
        } catch (NoSuchElementException e) {
            //do nothing
        }
    }

    public void addToCartListOfProducts(WebDriverWait wait, int productIndex) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtonListOfProducts));

        List<WebElement> addToCartButtons = driver.findElements(addToCartButtonListOfProducts);
        addToCartButtons.get(productIndex).click();
    }
}
