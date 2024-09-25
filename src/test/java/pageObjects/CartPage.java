package pageObjects;

import model.ApparelProduct;
import model.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartPage {

    protected WebDriver driver;
    public static final By terms = By.id("termsofservice");
    public static final By orderButton = By.id("checkout");
    public static final By headerMenu = By.className("header-menu");
    public static final By cellPhonesSection = By.partialLinkText("Cell phones");
    public static final String productPictureLocator = ".product-item .picture";
    public static final By colorField = By.id("product_attribute_3");
    public static final By memoryField = By.id("product_attribute_4");
    public static final By addToCartButton = By.className("add-to-cart-button");
    public static final By cartButton = By.cssSelector("span.cart-label");
    public static final By popup = By.id("bar-notification");
    public static final By popupErrors = By.cssSelector(".bar-notification.error > p");
    public static final By closePopupButton = By.cssSelector(".close");
    public static final By successPopUp = By.cssSelector(".bar-notification.success");
    public static final By firstAddToCartButtonListOfProducts = By.cssSelector(".button-2.product-box-add-to-cart-button:nth-child(1)");
    public static final By removeFromCart = By.cssSelector(".remove-btn");
    public static final By selectColorMemoryPath = By.xpath("//select[starts-with(@name, 'product_attribute_')]");
    public static final By voucherInput = By.id("discountcouponcode");
    public static final By voucherButton = By.id("applydiscountcouponcode");
    public static final By subTotalValue = By.className("value-summary");
    public static final By discount = By.className("discount-total");
    public static final By discountRemovalButton = By.className("remove-discount-button");
    public static final By productName = By.cssSelector(".product-name");
    public static final By productPrice = By.cssSelector(".product-unit-price");
    public static final By productQuantityInput = By.cssSelector(".product-quantity input");
    public static final By productTotalPrice = By.cssSelector(".product-subtotal");
    public static final By productAttributes = By.cssSelector(".attributes");
    public static final By cartQuantity = By.cssSelector("a .cart-qty");
    public static final By emptyCartMessage = By.cssSelector(".no-data");
    public static final By totalPriceCart = By.cssSelector(".order-total .value-summary");
    public static final By cartTable = By.cssSelector("#shopping-cart-form .table-wrapper");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int countShoppingCartElements() {
        try {
            List<WebElement> productElements = driver.findElements(By.cssSelector("td.product-picture"));
            return productElements.size();
        } catch (NoSuchElementException e) {
            // Handle the case when product elements are not found
            return 0;
        }
    }

    public List<Product> getProductsFromCart() {

        List<Product> products = new ArrayList<>();

        try {
            List<WebElement> productRows = driver.findElements(By.cssSelector(".cart tbody tr"));

            for (WebElement row : productRows) {
                boolean isApparel = isApparelProduct(row);
                Product product = isApparel ? new ApparelProduct() : new Product();
                product.setName(row.findElement(productName).getText());
                product.setPrice(new BigDecimal(row.findElement(productPrice).getText().replace("$", "")));
                product.setQuantity(Integer.parseInt(row.findElement(productQuantityInput).getAttribute("value")));
                product.setTotalPrice(new BigDecimal(row.findElement(productTotalPrice).getText().replace("$", "")));

                if (isApparel) {
                    ApparelProduct apparelProduct = (ApparelProduct) product;
                    apparelProduct.setSize(getAttribute(row, "Size"));
                    apparelProduct.setColor(getAttribute(row, "Color"));
                }
                products.add(product);
            }
            return products;

        } catch (NoSuchElementException e) {
            return products;
        }
    }

    public boolean isApparelProduct(WebElement row) {

        List<WebElement> attributesElements = row.findElements(productAttributes);

        if (attributesElements.isEmpty()) {
            return false;
        }

        String attributesContent = row.findElement(productAttributes).getText();

        boolean hasSize = attributesContent.contains("Size:");
        boolean hasColor = attributesContent.contains("Color:");

        return hasSize || hasColor;
    }

    private String getAttribute(WebElement row, String attribute) {
        try {
            String attributesText = row.findElement(productAttributes).getText();
            if (attributesText.contains(attribute)) {
                // Extract the value of the attribute
                String[] parts = attributesText.split("\n");
                for (String part : parts) {
                    if (part.contains(attribute)) {
                        return part.split(":")[1].trim();  // Extract the value after "Size:" or "Color:"
                    }
                }
            }
        } catch (NoSuchElementException e) {
            return null;
        }
        return null;  // Attribute not found
    }


    public void deleteAllProductsFromCart() {
        try {
            List<WebElement> removeFromCartButtons = driver.findElements(By.cssSelector("td.remove-from-cart"));

            if (!removeFromCartButtons.isEmpty()) {
                for (int i = 0; i < removeFromCartButtons.size(); i++) {
                    try {
                        driver.findElements(By.cssSelector("td.remove-from-cart")).get(0).click();
                        // Optionally, you can add a short delay if needed
                    } catch (Exception e) {
                        // Handle any exception that might occur while clicking the button
                        System.out.println("Exception occurred while removing product: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("No products to remove from the cart.");
            }
        } catch (NoSuchElementException ex) {
            // Handle the case where no "Remove from Cart" buttons are found
            System.out.println("No 'Remove from Cart' buttons found on the page.");
        }
    }

    public int getCartQuantityFromHeader() {
        return Integer.parseInt(driver.findElement(cartQuantity).getText().replaceAll("\\(", "").replaceAll("\\)", ""));
    }

    public void goToCategory(String category) {
        WebElement headerMenuElement = driver.findElement(headerMenu);
        headerMenuElement.findElement(By.linkText(category)).click();
    }


    public String getColorError(WebDriverWait wait) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(popup));
        List<WebElement> errors = driver.findElements(popupErrors);
        WebElement colorError = errors.get(0);
        return colorError.getText();
    }

    public String getMemoryError(WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(popup));
        List<WebElement> errors = driver.findElements(popupErrors);
        WebElement memoryError = errors.get(1);
        return memoryError.getText();
    }

    public void removeProduct(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(removeFromCart)).click();
    }

    public String getEmptyCartError(WebDriverWait wait) {
        WebElement noDataLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("no-data")));
        return noDataLocator.getText();
    }

    public void applyVoucher(String text) {
        WebElement voucherInputElement = driver.findElement(voucherInput);
        voucherInputElement.sendKeys(text);
        driver.findElement(voucherButton).click();

    }

    public String getCartSubTotal() {
        return driver.findElement(subTotalValue).getText();
    }

    public void removeDiscount() {
        try {
            driver.findElement(discountRemovalButton).click();
        } catch (NoSuchElementException e) {
            //do nothing
        }
    }

    public boolean discountPresent() {
        try {
            driver.findElement(discount);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void updateProductQuantity(int quantity) {
        driver.findElement(productQuantityInput).sendKeys(Keys.BACK_SPACE);
        driver.findElement(productQuantityInput).sendKeys(String.valueOf(quantity));
        driver.findElement(productQuantityInput).sendKeys(Keys.ENTER);
    }

    public BigDecimal getTotalPriceCart() {
        // Extract the text, remove the currency symbol, and trim any spaces
        String totalPriceText = driver.findElement(totalPriceCart).getText().replace("$", "").trim();

        // Convert the text to a BigDecimal
        return new BigDecimal(totalPriceText);
    }

    public void clickCheckoutProduct() {
        driver.findElement(terms).click();
        driver.findElement(orderButton).click();
    }

}
