package ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Product> products;

    public ShoppingCart() {
        products = new ArrayList<Product>();
    }

    public void addToShoppingCart(Product product){
        products.add(product);
    }

    public List<Product> getProducts(){
        return products;
    }

}
