package pageObjects;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ApparelPage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public static final String productPictureLocator = ".product-item .picture";
    public static final By redColor = By.cssSelector(".attribute-square-container[title='Red']");
    public static final By blueColor = By.cssSelector(".attribute-square-container[title='Blue']");
    public static final By silverColor = By.cssSelector(".attribute-square-container[title='Silver']");
    public static final By addToCartButton = By.className("add-to-cart-button");
    public static final By closePopupButton = By.cssSelector(".close");
    public static final By successPopUp = By.cssSelector(".bar-notification.success");
    public static final By addToCartButtonListOfProducts = By.cssSelector(".button-2.product-box-add-to-cart-button");
    public static final By selectSizePath = By.xpath("//select[starts-with(@name, 'product_attribute_')]");
    public static final By colorDropdown = By.xpath("//label[contains(text(), 'Color')]/following::dd//select");
    public static final By printImage = By.cssSelector("span.attribute-square");
    public static final By productPrice = By.cssSelector(".product-price span");
    public static final By productPriceListOfProducts = By.cssSelector(".price.actual-price");
    public static final By productName = By.cssSelector(".product-name h1");
    public static final By reviewList = By.cssSelector(".product-review-list");
    public static final By errorBar = By.cssSelector(".bar-notification.error");
    public static final By closeErrorBar = By.cssSelector(".bar-notification.error .close");

    public ApparelPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToProduct(int productIndex) {
        List<WebElement> productsOnListPage = driver.findElements(By.cssSelector(productPictureLocator));
        WebElement productRequired = productsOnListPage.get(productIndex);
        productRequired.click();
    }

    public void waitForProductFieldsToBeVisible(WebDriverWait wait) {
        List<WebElement> selectElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selectSizePath));
        wait.until(ExpectedConditions.elementToBeClickable(selectElements.get(0)));
    }

    public void selectSizeDropdown(String size) {
        Select sizeDropdown = new Select(driver.findElement(selectSizePath));
        sizeDropdown.selectByVisibleText(size);
    }

    public void selectColorImage(String color) {

        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("Color cannot be null or empty");
        }

        switch (color.toLowerCase()) {
            case "red":
                driver.findElement(redColor).click();
                break;
            case "silver":
                driver.findElement(silverColor).click();
                break;
            case "blue":
                driver.findElement(blueColor).click();
                break;

            default:
                throw new IllegalArgumentException("Invalid color: " + color);
        }

    }

    public void selectColorDropdown(int index) {
        Select sizeDropdown = new Select(driver.findElement(colorDropdown));
        sizeDropdown.selectByIndex(index);
    }

    public void selectPrintImage(int index) {
        List<WebElement> webElements = driver.findElements(printImage);
        webElements.get(index).click();
    }

    public String getPrice() {
        return driver.findElement(productPrice).getText().replace("$", "");
    }

    public String getProductName() {
        return driver.findElement(productName).getText();
    }

    public void clickAddToCart() {
        driver.findElement(addToCartButton).click();
    }

    public boolean isErrorBarVisible() {
        try {
            WebElement errorBarElement = driver.findElement(errorBar);
            return errorBarElement.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void addToCartFromListOfProducts(WebDriverWait wait, int productIndex) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtonListOfProducts));

        List<WebElement> addToCartButtons = driver.findElements(addToCartButtonListOfProducts);
        addToCartButtons.get(productIndex).click();
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        List<WebElement> productRows = driver.findElements(By.cssSelector(".product-title"));
        int countProducts = productRows.size();

        for (int i = 0; i < countProducts; i++) {
            Product product = new Product();
            product.setName(driver.findElements(By.cssSelector(".product-title")).get(0).getText());
            product.setPrice(new BigDecimal(driver.findElements(productPriceListOfProducts).get(0).getText().replace("$", "")));
            products.add(product);
        }
        return products;
    }

}
