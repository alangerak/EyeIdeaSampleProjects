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

    private static void createProductDatabase(){
        ProductDBConnector productDBConnector = ProductDBConnector.getInstance();

        productDBConnector.registerProduct("Pizza", ProductCategory.FOOD, 5.99f);
        productDBConnector.registerProduct("Beer", ProductCategory.ALCOHOL_DRINKS, 9.99f);
        productDBConnector.registerProduct("AllPurposeCleaner", ProductCategory.CLEANING_SUPPLIES, 2.40f);
        productDBConnector.registerProduct("DiscoLights", ProductCategory.ELECTRONICS, 2.40f);
    }


    public static void main(String[] args) {

        createProductDatabase();
        List<Product> products = new ArrayList<>();

        products.add(new Product("Pizza"));
        products.add(new Product("Beer"));
        products.add(new Product("AllPurposeCleaner"));
        products.add(new Product("DiscoLights"));
        products.add(new Product("Null"));

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

        BigDecimal totalAmount = CheckoutCalculator.truncate(validator.getTotalAmount());

        if (totalAmount.compareTo(new BigDecimal("19.98")) == 0) {
            validator.success();
        } else if(totalAmount.compareTo(new BigDecimal("19.98")) < 0){
            System.out.println("[Error], the calculated amount is smaller than was expected");
        } else if(totalAmount.compareTo(new BigDecimal("19.98")) > 0){
            System.out.println("[Error], the calculated amount is bigger than was expected");
        }

    }

}
