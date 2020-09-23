package ShoppingCart;

import jdk.jfr.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductDBConnector {

    private static ProductDBConnector productBuilder;

    /**
     * Simulates the database
     */
    private Map<String, Product> nameProductDatabase;
    private Map<Product, ProductCategory> productProductCategoryMap;
    private Map<ProductCategory, TaxType> productCategoryTaxTypeMap;
    private Map<Pair<CountryCode, TaxType>, Float> taxValueMap;
    private Map<Product, Float> productPriceMap;



    private ProductDBConnector(){
        nameProductDatabase = new HashMap<>();
        productProductCategoryMap = new HashMap<>();
        productCategoryTaxTypeMap = new HashMap<>();
        productPriceMap = new HashMap<>();
        taxValueMap = new HashMap<>();

        buildProductCategoryTaxMap();
        buildTaxMap();
    }

    private void buildProductCategoryTaxMap(){
        productCategoryTaxTypeMap.put(ProductCategory.FOOD, TaxType.ESSENTIAL);
        productCategoryTaxTypeMap.put(ProductCategory.ELECTRONICS, TaxType.LUXURY);
        productCategoryTaxTypeMap.put(ProductCategory.CLEANING_SUPPLIES, TaxType.ESSENTIAL);
        productCategoryTaxTypeMap.put(ProductCategory.ALCOHOL_DRINKS, TaxType.LUXURY);
    }

    private void buildTaxMap(){
        taxValueMap.put(new Pair<>(CountryCode.NL, TaxType.ESSENTIAL), 0.09f);
        taxValueMap.put(new Pair<>(CountryCode.NL, TaxType.LUXURY), 0.21f);

        taxValueMap.put(new Pair<>(CountryCode.GB, TaxType.ESSENTIAL), 0.05f);
        taxValueMap.put(new Pair<>(CountryCode.GB, TaxType.LUXURY), 0.20f);
    }

    public static ProductDBConnector getInstance(){
        if(productBuilder == null) productBuilder = new ProductDBConnector();
        return productBuilder;
    }


    public Product registerProduct(String name, ProductCategory category, float price){
        Product product = new Product(name);
        nameProductDatabase.put(name, product);
        productProductCategoryMap.put(product, category);
        productPriceMap.put(product, price);

        return product;
    }

    public float getPriceProduct(Product product){
        return productPriceMap.getOrDefault(product, -1f);
    }

    public float getTaxRate(Product product, CountryCode code){
        if(productProductCategoryMap.containsKey(product)){
            TaxType taxType = productCategoryTaxTypeMap.get(productProductCategoryMap.get(product));
            return taxValueMap.get(new Pair<>(code, taxType));
        }else{
            return -1f;
        }
    }


    private static class Pair<S,T>{

        private final S s;
        private final T t;

        public Pair(S s, T t){
            this.s = s;
            this.t = t;
        }

        public S getS() {
            return s;
        }

        public T getT() {
            return t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(s, pair.s) &&
                    Objects.equals(t, pair.t);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s, t);
        }
    }

}
