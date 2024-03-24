package com.example.app_bandienthoai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.ProductCartAdapter;
import common.ProductCart;
import models.Product;
import models.User;
import reference.Reference;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class TrangGioHang extends AppCompatActivity {
    private final Reference reference = new Reference();

    private ListView list_view_cart;

    ImageButton button1;

    ProductCartAdapter product_cart_adapter;

    ArrayList<ProductCart> products_cart = new ArrayList<>();

    CheckBox total_check;

    TextView text_view_selected_count, text_view_total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().hide();


        DatabaseReference users_ref = reference.getUsers();

        DatabaseReference products_ref = reference.getProducts();

        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        list_view_cart = findViewById(R.id.list_view_cart);

        mapping_client();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangGioHang.this, TrangChu.class);
                startActivity(intent);
            }
        });

        users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null && user.getCart() != null) {

                    HashMap<String, Integer> hash_products = user.getCart().get_products();

                    if (product_cart_adapter == null) {
                        products_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                products_cart = new ArrayList<>();

                                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                                    Product product = productSnapshot.getValue(Product.class);

                                    String product_id = product.getId();

                                    if (hash_products.containsKey(product_id)) {
                                        if (hash_products.get(product_id) > 0) {
                                            ProductCart product_cart = new ProductCart(product, hash_products.get(product_id));

                                            products_cart.add(product_cart);
                                        }
                                    }
                                }
                                HandleProductCart hander = new HandleProductCart();
                                product_cart_adapter = new ProductCartAdapter(TrangGioHang.this, products_cart, hander);
                                list_view_cart.setAdapter(product_cart_adapter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        total_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleProductCart _h = new HandleProductCart();

                if (total_check.isChecked()) {
                    for (int i = 0; i < products_cart.size(); i++) {
                        String id = products_cart.get(i).product.getId();
                        product_cart_adapter.id_products_selected.put(i, id);
                    }

                    product_cart_adapter.notifyDataSetChanged();
                    _h.update_total_price();

                } else {
                    product_cart_adapter.id_products_selected.clear();
                    product_cart_adapter.notifyDataSetChanged();
                    _h.update_total_price();
                }
            }
        });

    }

    void mapping_client() {
        this.button1 = findViewById(R.id.button1);
        total_check = findViewById(R.id.total_check);
        text_view_selected_count = findViewById(R.id.selected_count);
        text_view_total_price = findViewById(R.id.total);
    }

    public class HandleProductCart {

        public HandleProductCart() {
        }

        public void remove_product_cart(int position) {
            products_cart.remove(position);
            product_cart_adapter.notifyDataSetChanged();
        }

        public void update_products_cart(int position, ProductCart product) {
            products_cart.set(position, product);
        }

        public void update_change_check_box() {
            if (products_cart != null) {

                int selected_count = product_cart_adapter.id_products_selected.size();

                total_check.setChecked(selected_count == products_cart.size());

                text_view_selected_count.setText("(" + selected_count + ")");

                update_total_price();
            }
        }

        public void update_total_price() {
            int price = 0;

            for (int i = 0; i < product_cart_adapter.id_products_selected.size(); i++) {
                int key = product_cart_adapter.id_products_selected.keyAt(i);
                String value = product_cart_adapter.id_products_selected.valueAt(i);

                ProductCart _p = products_cart.get(key);

                if (_p.product.getId() == value) {
                    price += _p.quantity * _p.product.getPrice();
                }
            }

            text_view_total_price.setText(Integer.toString(price));

        }
    }
}