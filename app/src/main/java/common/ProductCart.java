package common;

import models.Product;

public class ProductCart {
    public int quantity;

    public Product product;


    public ProductCart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


}
