package ShoppingCart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckoutCalculator {

    private final ShoppingCart shoppingCart;
    private final ProductDBConnector productDBConnector;

    public CheckoutCalculator(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        productDBConnector = ProductDBConnector.getInstance();
    }

    public float applyCouponBeforeTax(Coupon coupon){
        float total = 0;
        for(Product product: shoppingCart.getProducts()){
            float price = productDBConnector.getPriceProduct(product);
            total += price - price * coupon.getDiscount();
        }
        return total;
    }

    public float applyTax(CountryCode countryCode){
        float total = 0;
        for(Product product: shoppingCart.getProducts()){
            float price = productDBConnector.getPriceProduct(product);
            float taxRate = productDBConnector.getTaxRate(product, countryCode);
            if(taxRate != -1){
                total += price + price * taxRate;
            }
        }
        return total;
    }


    public static BigDecimal truncate(float price){
        BigDecimal bd = new BigDecimal(Float.toString(price));
        bd = bd.setScale(2, RoundingMode.HALF_DOWN);
        return bd;
    }

}




