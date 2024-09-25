package pageObjects;

import model.ApparelProduct;
import model.OrderDetails;
import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderHistory {

    protected WebDriver driver;
    public static final By orderDiv = By.cssSelector(".section.order-item");
    public static final By orderTitle = By.cssSelector(".title");
    public static final By orderStatus = By.cssSelector(".order-status");
    public static final By orderDate = By.cssSelector(".order-date");
    public static final By orderTotal = By.cssSelector(".order-total");
    public static final By orderDetailsButton = By.cssSelector("button.button-2.order-details-button");
    public static final By tableRows = By.cssSelector(".table-wrapper tbody tr");
    public static final By productAttributes = By.cssSelector(".attributes");
    public static final By productPrice = By.cssSelector(".product-unit-price");
    public static final By productQuantity = By.cssSelector(".product-quantity");
    public static final By productTotalPrice = By.cssSelector(".product-subtotal");



    public OrderHistory(WebDriver driver) {
        this.driver = driver;
    }

    public List<OrderDetails> getOrders(){

        List<OrderDetails> orderDetailsList = new ArrayList<>();

        // Locate all orders
        List<WebElement> orderItems = driver.findElements(orderDiv);

        for (WebElement orderItem : orderItems) {
            String orderNumber = orderItem.findElement(orderTitle).getText().replace("Order Number: ", "");
            String orderStatus = orderItem.findElement(this.orderStatus).getText();
            String orderDate = orderItem.findElement(this.orderDate).getText();
            String orderTotal = orderItem.findElement(this.orderTotal).getText();

            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderNumber(orderNumber);
            orderDetails.setOrderStatus(orderStatus);
            orderDetails.setOrderDate(orderDate);
            orderDetails.setOrderTotal(orderTotal);

            orderDetailsList.add(orderDetails);
        }

        return orderDetailsList;
    }

    public void goToNthOrder(int nthOrder){
        List<WebElement> orderDetailsButtons = driver.findElements(orderDetailsButton);
        orderDetailsButtons.get(nthOrder).click();
    }

    public List<Product> getProductsInOrder() {

        List<Product> products = new ArrayList<>();

        try {
            List<WebElement> productRows = driver.findElements(tableRows);

            for (WebElement row : productRows) {
                boolean isApparel = isApparelProduct(row);
                Product product = isApparel ? new ApparelProduct() : new Product();
                product.setName(row.findElement(By.cssSelector("em a")).getText());
                product.setPrice(new BigDecimal(row.findElement(productPrice).getText().replace("$", "")));
                product.setQuantity(Integer.parseInt(row.findElement(productQuantity).getText()));
                product.setTotalPrice(new BigDecimal(row.findElement(productTotalPrice).getText().replace("$", "")));

                if(isApparel){
                    ApparelProduct apparelProduct = (ApparelProduct)product;
                    apparelProduct.setSize(getAttribute(row,"Size"));
                    apparelProduct.setColor(getAttribute(row,"Color"));
                }
                products.add(product);
            }
            return products;

        } catch (NoSuchElementException e) {
            return products;
        }
    }

    public boolean isApparelProduct(WebElement row){
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

}
