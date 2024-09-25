package tests;

import constants.OrderCheckoutMessages;
import constants.Urls;
import model.ApparelProduct;
import model.BillingDetails;
import model.OrderDetails;
import model.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;
import utils.NavigationUtils;
import utils.PlaceOrderUtils;

import java.time.Duration;
import java.util.List;

import static constants.OrderCheckoutMessages.SUCCESSFUL_ORDER;

public class CheckoutTests extends BaseTest{
    private LoginPage loginPage;
    private CartPage cartPage;
    private ApparelPage apparelPage;
    private CheckoutPage checkoutPage;
    private OrderHistory orderHistory;
    private OrderDetailsPage orderDetailsPage;
    WebDriver driver;
    WebDriverWait wait;
    BillingDetails billingDetails;

    @BeforeClass
    public void login()
    {
        driver = browserSetup();
        wait= new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
        driver.get(Urls.BASE_URL+ Urls.LOGIN_PATH);
        loginPage.login("admin@admin.com","admin");
        loginPage.clickLogin();
//
    }

    @BeforeMethod
    public void setup() {
        cartPage = new CartPage(driver);
        apparelPage = new ApparelPage(driver, wait);
        checkoutPage = new CheckoutPage(driver);
        orderHistory = new OrderHistory(driver);
        orderDetailsPage = new OrderDetailsPage(driver);

        driver.get(Urls.BASE_URL);
        NavigationUtils.goToShoppingCart(wait, driver);
        cartPage.deleteAllProductsFromCart();
    }

    @AfterClass
    public void afterTest() {
        BaseTest.tearDown(driver);
    }

//    check that user can order a product and the order is displayed in my account section + it contains the right product
    @Test
    public void orderProduct(){
        NavigationUtils.goToApparelShoes(driver);

        ApparelProduct expectedProduct = PlaceOrderUtils.addApparelProductToCart(apparelPage,0,"9","Blue");

        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToShoppingCart(wait, driver);

        cartPage.clickCheckoutProduct();
        wait.until(ExpectedConditions.urlToBe(Urls.BASE_URL+ Urls.CHECKOUT_PATH));

        boolean isASameAddressChecked = checkoutPage.checkIfShipToSameAddressIsChecked();
        if (!isASameAddressChecked){
            checkoutPage.checkShipToSameAddressCheckbox();
        }

        billingDetails = new BillingDetails("Oana","Testing","oana@yopmail.com",
                "Romania","Cluj", "Cluj-Napoca", "Aurel Vlaicu","400000","0700000000");
        checkoutPage.completeAllSteps(billingDetails, wait);

        wait.until(ExpectedConditions.urlToBe(Urls.BASE_URL+ Urls.CHECKOUT_COMPLETED_PATH));
        Assert.assertEquals(checkoutPage.getSuccessfulMessage(),SUCCESSFUL_ORDER);

        String orderNoCheckout = checkoutPage.getOrderNumber();
        NavigationUtils.goToOrders(driver,wait);

        List<OrderDetails> listOfOrders = orderHistory.getOrders();
        Assert.assertEquals(orderNoCheckout,listOfOrders.get(0).getOrderNumber());

        orderHistory.goToNthOrder(0);
        wait.until(ExpectedConditions.visibilityOfElementLocated(OrderHistory.tableRows));
        List<Product> productsInOrder = orderHistory.getProductsInOrder();

        Assert.assertEquals(productsInOrder.size(),1);
        ApparelProduct apparelProductInOrder = (ApparelProduct) productsInOrder.get(0);
        Assert.assertEquals(expectedProduct.getName(),apparelProductInOrder.getName());
        Assert.assertEquals(expectedProduct.getSize(),apparelProductInOrder.getSize());
        Assert.assertEquals(expectedProduct.getColor(),apparelProductInOrder.getColor());
        Assert.assertEquals(expectedProduct.getPrice(),apparelProductInOrder.getPrice());

    }

//    check that order details are saved correctly
    @Test(dependsOnMethods = {"orderProduct"})
    public void checkOrderDetails(){
        NavigationUtils.goToOrders(driver,wait);
        orderHistory.goToNthOrder(0);
        wait.until(ExpectedConditions.visibilityOfElementLocated(OrderHistory.tableRows));

        BillingDetails actualBillingDetails = orderDetailsPage.getBillingDetails();
        Assert.assertEquals(actualBillingDetails,billingDetails);

    }
}
