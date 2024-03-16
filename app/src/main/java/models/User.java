package models;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private Date date_of_birth;
    private String phone;
    private ArrayList<Voucher> vouchers;

    private ArrayList<User_Notification> user_notifications;

    public ArrayList<User_Notification> getUser_notifications() {
        return user_notifications;
    }

    public void setUser_notifications(ArrayList<User_Notification> user_notifications) {
        this.user_notifications = user_notifications;
    }

    public User(String id, String name, String email, String password, Date date_of_birth, String phone, ArrayList<Voucher> vouchers, ArrayList<User_Notification> user_notifications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.vouchers = vouchers;
        this.user_notifications = user_notifications;
    }

    public User(String id, String name, String email, String password, Date date_of_birth, String phone, ArrayList<Voucher> vouchers) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date_of_birth = date_of_birth;
        this.phone = phone;
        this.vouchers = vouchers;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(ArrayList<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

}
