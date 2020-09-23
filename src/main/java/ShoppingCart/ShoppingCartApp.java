package ShoppingCart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This task consist out of a simulation a Shopping cart module.
 * Such a module would run together with various other modules on an Ecommerce platform and
 * is responsible for keeping track of the current ordered items as well as calculating the total
 * price, including taxes.
 */
public class ShoppingCartApp {

    public static void main(String[] args) {

        ProductDBConnector productDBConnector = ProductDBConnector.getInstance();
        List<Product> products = new ArrayList<>();

        products.add(productDBConnector.registerProduct("Pizza", ProductCategory.FOOD, 5.99f));
        products.add(productDBConnector.registerProduct("Beer", ProductCategory.ALCOHOL_DRINKS, 9.99f));
        products.add(productDBConnector.registerProduct("AllPurposeCleaner", ProductCategory.CLEANING_SUPPLIES, 2.40f));
        products.add(productDBConnector.registerProduct("DiscoLights", ProductCategory.ELECTRONICS, 2.40f));

        ShoppingCart shoppingCart = new ShoppingCart();
        CheckoutCalculator checkoutCalculator = new CheckoutCalculator(shoppingCart);

        for (Product product : products) {
            shoppingCart.addToShoppingCart(product);
        }

        checkout(checkoutCalculator);
    }

    private static void checkout(CheckoutCalculator checkoutCalculator) {
        Validator validator = new Validator();

        validator.addAmount(checkoutCalculator.applyCouponBeforeTax(new Coupon(0.2f)));
        validator.addAmount(checkoutCalculator.applyTax(CountryCode.NL));

        if (CheckoutCalculator.truncate(validator.getTotalAmount()).compareTo(new BigDecimal("19.98")) == 0) {
            validator.success();
        } else {
            System.out.println("Something went wrong with calculating the result");
        }

    }

}
