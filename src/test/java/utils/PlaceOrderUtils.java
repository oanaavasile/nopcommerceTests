package utils;

import constants.Urls;
import model.ApparelProduct;
import pageObjects.ApparelPage;

import java.math.BigDecimal;

import static constants.OrderCheckoutMessages.SUCCESSFUL_ORDER;

public class PlaceOrderUtils {

    public static ApparelProduct addApparelProductToCart(ApparelPage apparelPage, int productIndex, String size, String color){
        ApparelProduct product = new ApparelProduct();

        apparelPage.goToProduct(productIndex);
        apparelPage.selectSizeDropdown(size);
        apparelPage.selectColorImage(color);

        product.setSize(size);
        product.setColor(color);
        product.setPrice(new BigDecimal(apparelPage.getPrice()));
        product.setName(apparelPage.getProductName());

        apparelPage.clickAddToCart();

        return product;
    }



}