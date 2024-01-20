package com.example.app_bandienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import models.Invoice;
import models.Order;
import models.Product;
import models.User;
import models.Voucher;
import reference.Reference;

public class TrangChu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        //create database user
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference user_ref = database.getReference("Users");
//        String key = user_ref.push().getKey();
//
//        ArrayList<Voucher> vouchers = new ArrayList<>();
//        vouchers.add(new Voucher("1", "voucher 1", "voucher 1", "voucher 1", 100, 100));
//        User user = new User(key, "ha", "ha@gmail.com", "123456", new Date(), "123456789", vouchers);
//        user_ref.child(user.getId()).setValue(user);
//
//        String key2 = user_ref.push().getKey();
//        ArrayList<Voucher> vouchers2 = new ArrayList<>();
//        vouchers2.add(new Voucher("2", "voucher 2", "voucher 2", "voucher 2", 200, 200));
//        User user2 = new User(key2, "ha2", "ha2@gmail.com", "654321", new Date(), "987654321", vouchers2);
//        user_ref.child(user2.getId()).setValue(user2);
//

        // query user

//        TextView profileName = findViewById(R.id.profileName);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        DatabaseReference usersRef = database.getReference("Users");
//
//        Query query = usersRef.orderByChild("email").equalTo("ha@gmail.com");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                        User user = userSnapshot.getValue(User.class);
//                        if (user.getPassword().equals("123456"))
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    profileName.setText(user.getName());
//                                }
//                            });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle possible errors.
//                Log.e("Main", ">>>>>>>>>>>>> bug");
//            }
//        });

        Reference reference = new Reference();

        // create product
//        DatabaseReference product_ref = reference.getProducts();
//
//        String product_key = product_ref.push().getKey();
//
//        Product product = new Product(product_key, "product 1", "product 1", 100, 100, 100, 100, "image", "category", "brand", "color");
//
//        product_ref.child(product.getId()).setValue(product);


        // create invoice
//        DatabaseReference invoice_ref = reference.getInvoices();
//
//        String invoice_key = invoice_ref.push().getKey();
//
//        ArrayList<Order> orders = new ArrayList<>();
//
//        orders.add(new Order("-NobF7qrkx6kpn_u-NfD", 100, 500000, 20000));
//        orders.add(new Order("-NobF7qrkx6kpn_u-NfD", 100, 500000, 20000));
//
//
//        Invoice invoice = new Invoice(invoice_key, "Nob89HQTwIrLyH_oicW", "ha noi", 5000, 20, new Date(), orders);
//
//        invoice_ref.child(invoice.getId()).setValue(invoice);
    }
}