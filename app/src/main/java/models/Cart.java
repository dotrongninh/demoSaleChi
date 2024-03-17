package models;

import java.util.HashMap;

public class Cart {
    // key is id of product
    // value is quantity
    private HashMap<String, Integer> products;

    public Cart(HashMap<String, Integer> products) {
        this.products = products;
    }

    public Cart(){
        this.products = new HashMap<>();
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }
}
