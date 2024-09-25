package tests;

import constants.*;
import model.ApparelProduct;
import model.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.*;
import tests.dataProviders.TestDataProvider;
import utils.NavigationUtils;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class AddToCartTests extends BaseTest {
    private LoginPage loginPage;
    private CartPage cartPage;
    private ApparelPage apparelPage;
    WebDriver driver;
    private ElectronicsPage electronicsPage;
    WebDriverWait wait;
    @BeforeClass
    public void register() {
        driver = browserSetup();
        wait= new WebDriverWait(driver, Duration.ofSeconds(10));
//        registerPage = new RegisterPage(driver, new WebDriverWait(driver, Duration.ofSeconds(10)));
//        RandomRegistrationData randomRegistrationData = new RandomRegistrationData();
//        randomRegistrationData.generateRandomAll();
//        randomRegistrationData.setPassword(password);
//
//        driver.get(Urls.BASE_URL+ Urls.REGISTER_PATH);
//        registerPage.completePersonalDetails(randomRegistrationData);
//        registerPage.completeCompanyDetails(randomRegistrationData.getCompanyName());
//        registerPage.completePassword(password, password);
//        registerPage.clickRegister();
//
//        Assert.assertEquals(registerPage.getRegistrationMessage(), RegistrationMessages.REGISTRATION_SUCCESS);
//        Assert.assertTrue(registerPage.isMyAccountIconVisible(), "My Account icon is not visible.");
//
//        newEmail = randomRegistrationData.getEmail();
    }

    @BeforeMethod
    public void setup() {
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
        apparelPage = new ApparelPage(driver, wait);
        electronicsPage = new ElectronicsPage(driver);
        driver.get(Urls.BASE_URL);
        NavigationUtils.goToShoppingCart(wait, driver);
        cartPage.deleteAllProductsFromCart();
    }

    @AfterClass
    public void afterTest() {
        BaseTest.tearDown(driver);
    }

    //    test user can add product to shopping cart
    @Test(dataProvider = "shoesAttributes",dataProviderClass = TestDataProvider.class)
    public void productAddedToShoppingCart(String size, String color) throws InterruptedException {
        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);

        apparelPage.goToProduct(0);
        apparelPage.selectSizeDropdown(size);
        apparelPage.selectColorImage(color);

        Product expectedProduct = new Product();
        expectedProduct.setPrice(new BigDecimal(apparelPage.getPrice()));
        expectedProduct.setName(apparelPage.getProductName());


        apparelPage.clickAddToCart();
        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToShoppingCart(wait, driver);
        List<Product> productsAddedToCart = cartPage.getProductsFromCart();

        Assert.assertEquals(cartPage.countShoppingCartElements(), 1);
        Assert.assertEquals(expectedProduct.getName(), productsAddedToCart.get(0).getName());
        Assert.assertEquals(expectedProduct.getPrice(), productsAddedToCart.get(0).getPrice());

    }

//     on click 'Add to cart' from the list of products, user should be redirected to product details page
    @Test
    public void userRedirectedToProductDetailsPage() {
        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);

//      click add to cart on the first product on the page
        List<Product> productsInList = apparelPage.getProducts();
        apparelPage.addToCartFromListOfProducts(wait, 0);

//      user should be redirected to product details page
        wait.until(ExpectedConditions.textToBePresentInElementLocated(NavigationUtils.currentItemBreadcrumb, productsInList.get(0).getName()));
        Assert.assertNotEquals(driver.getCurrentUrl(), Urls.BASE_URL + Urls.SHOES_PATH);
        Assert.assertNotEquals(driver.getCurrentUrl(), Urls.BASE_URL + Urls.CART_PATH);

//        check that user was redirected to the correct product
        Assert.assertEquals(productsInList.get(0).getName(), apparelPage.getProductName());
        Assert.assertEquals(cartPage.getCartQuantityFromHeader(), 0);
    }

    //    Check that 2 products of different types can be added to cart
    @Test
    public void addToCart2DifferentProducts() {
        NavigationUtils.goToElectronicsSection(driver);
        NavigationUtils.goToCameraAndPhoto(driver);
        electronicsPage.goToProduct(2);

//        initialize the electronicProduct object and set its attributes
//        then add product to cart
        Product electronicProduct = new Product();
        electronicProduct.setName(electronicsPage.getProductName());
        electronicProduct.setPrice(new BigDecimal(electronicsPage.getPrice()));
        electronicProduct.setQuantity(1);
        electronicProduct.setTotalPrice(new BigDecimal(electronicsPage.getPrice()));
        electronicsPage.clickAddToCart();

        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);

        String prodColor = "Blue";
        String prodSize = "9";

        apparelPage.goToProduct(0);
        apparelPage.selectSizeDropdown(prodSize);
        apparelPage.selectColorImage(prodColor);

//        initialize the apparelProduct object and set its attributes
//        then add product to cart
        ApparelProduct apparelProduct = new ApparelProduct();
        apparelProduct.setPrice(new BigDecimal(apparelPage.getPrice()));
        apparelProduct.setName(apparelPage.getProductName());
        apparelProduct.setQuantity(1);
        apparelProduct.setTotalPrice(new BigDecimal(electronicsPage.getPrice()));
        apparelProduct.setColor(prodColor);
        apparelProduct.setSize(prodSize);
        apparelPage.clickAddToCart();

        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToShoppingCart(wait, driver);

//        get products from cart and check that there are exactly 2 products in the cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(CartPage.cartTable));
        List<Product> productsInCart = cartPage.getProductsFromCart();
        Assert.assertEquals(productsInCart.size(), 2);

//        determine if the electronic product exists in the cart
        boolean electronicProductExists = productsInCart.stream()
                .anyMatch(product -> product.equals(electronicProduct));

//        determine if the apparel product exists in the cart
        boolean apparelProductExists = productsInCart.stream()
                .anyMatch(product -> product.equals(apparelProduct));

//        assert that the products are present in the cart
        Assert.assertTrue(electronicProductExists, "The electronic is not present in the cart.");
        Assert.assertTrue(apparelProductExists, "The apparel product is not present in the cart.");
    }

    //    Check that product is not added to cart if the required fields are not selected
    @Test
    public void addProductWithoutRequiredFields() throws InterruptedException {
        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);

//        click add to cart without selecting the Size
        apparelPage.goToProduct(0);
        apparelPage.clickAddToCart();
        NavigationUtils.waitForErrorBarToBeVisible(wait);
        NavigationUtils.closeErrorBar(driver);

        Assert.assertEquals(cartPage.getCartQuantityFromHeader(), 0);
    }

    //    Check that products can be removed from cart
    @Test
    public void deleteProductFromCart() {
        NavigationUtils.goToElectronicsSection(driver);
        NavigationUtils.goToCameraAndPhoto(driver);
        electronicsPage.goToProduct(2);
        electronicsPage.clickAddToCart();
        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToShoppingCart(wait, driver);

//        check that there is exactly 1 product in cart
        List<Product> productsInCart = cartPage.getProductsFromCart();
        Assert.assertEquals(productsInCart.size(), 1);

        cartPage.removeProduct(wait);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPage.emptyCartMessage));

//        check that after removing the product the shopping cart is empty
        List<Product> productsAfterRemove = cartPage.getProductsFromCart();
        Assert.assertEquals(productsAfterRemove.size(), 0);
        Assert.assertEquals(cartPage.getEmptyCartError(wait), ShoppingCartMessages.EMPTY_CART_MESSAGE);

    }

    //    Check that the quantity can be updated
    @Test
    public void updateProductQuantity() {

        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);

        apparelPage.goToProduct(0);
        apparelPage.selectSizeDropdown("9");
        apparelPage.selectColorImage("Blue");

        Product productToAdd = new Product();
        productToAdd.setPrice(new BigDecimal(apparelPage.getPrice()));
        productToAdd.setName(apparelPage.getProductName());
        productToAdd.setQuantity(1);
        productToAdd.setTotalPrice(new BigDecimal(apparelPage.getPrice()));
        apparelPage.clickAddToCart();

        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToShoppingCart(wait, driver);
        List<Product> productsAddedToCart = cartPage.getProductsFromCart();

        Assert.assertEquals(productsAddedToCart.get(0).getName(), productToAdd.getName());
        Assert.assertEquals(productsAddedToCart.size(), 1);

        int productQuantity = 4;
        cartPage.updateProductQuantity(productQuantity);

        List<Product> productsAfterQuantityUpdate = cartPage.getProductsFromCart();
        Assert.assertEquals(productsAfterQuantityUpdate.get(0).getQuantity(), productQuantity);

        BigDecimal expectedTotalPrice = productsAfterQuantityUpdate.get(0).getPrice().multiply(new BigDecimal(productQuantity));
        Assert.assertEquals(productsAfterQuantityUpdate.get(0).getTotalPrice(), expectedTotalPrice);

//        Check that cart total price is correct
        Assert.assertEquals(expectedTotalPrice, cartPage.getTotalPriceCart());

    }

//    check that user can add to cart the same product but with different attributes (e.g. size, color)
    @Test
    public void addSameProductDifferentAttributes() {
        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);

        String firstProductExpectedSize = "9";
        String firstProductExpectedColor = "Red";
        apparelPage.goToProduct(0);
        apparelPage.selectSizeDropdown(firstProductExpectedSize);
        apparelPage.selectColorImage(firstProductExpectedColor);
        apparelPage.clickAddToCart();

        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToApparelSection(driver);
        NavigationUtils.goToShoesSection(driver);
        apparelPage.goToProduct(0);

        String secondProductExpectedSize = "10";
        String secondProductExpectedColor = "Blue";
        apparelPage.selectSizeDropdown(secondProductExpectedSize);
        apparelPage.selectColorImage(secondProductExpectedColor);
        apparelPage.clickAddToCart();

        NavigationUtils.closeOverlayingPopUp(wait, driver);
        NavigationUtils.goToShoppingCart(wait, driver);
        List<Product> productsAddedToCart = cartPage.getProductsFromCart();

        Assert.assertEquals(productsAddedToCart.size(), 2, "The cart does not contain enough products.");

        ApparelProduct firstProduct = (ApparelProduct) productsAddedToCart.get(0);
        Assert.assertEquals(firstProductExpectedSize, firstProduct.getSize(), "Size of first product does not match");
        Assert.assertEquals(firstProductExpectedColor, firstProduct.getColor(), "Color of first product does not match");

        ApparelProduct secondProd = (ApparelProduct) productsAddedToCart.get(1);
        Assert.assertEquals(secondProductExpectedSize, secondProd.getSize(), "Size of second product does not match");
        Assert.assertEquals(secondProductExpectedColor, secondProd.getColor(), "Color of second product does not match");

    }
}