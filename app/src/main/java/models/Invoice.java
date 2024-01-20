package models;

import java.util.ArrayList;
import java.util.Date;

public class Invoice {
    private String id;
    private String user_id;
    private String address;
    private float total;
    private float discount;
    private Date create_at;
    private ArrayList<Order> orders;

    public Invoice(String id, String user_id, String address, float total, float discount, Date create_at, ArrayList<Order> orders) {
        this.id = id;
        this.user_id = user_id;
        this.address = address;
        this.total = total;
        this.discount = discount;
        this.create_at = create_at;
        this.orders = orders;
    }

    public Invoice() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
