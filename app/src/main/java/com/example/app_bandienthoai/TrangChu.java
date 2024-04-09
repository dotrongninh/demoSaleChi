package com.example.app_bandienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapters.ProductIndexAdapter;
import models.Product;
import reference.Reference;
import services.ProductService;

public class TrangChu extends AppCompatActivity {
    ImageButton bt_KhuyenMai, bt_ThongBao, bt_GioHang, bt_TTCaNhan, image_button_search;

    private EditText edit_text_search_value;

    private final Reference reference = new Reference();
    private final ProductService product_service = new ProductService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        getSupportActionBar().hide();

        bt_ThongBao = findViewById(R.id.button3);
        bt_TTCaNhan = findViewById(R.id.button5);
        bt_KhuyenMai = findViewById(R.id.button2);
        bt_GioHang = findViewById(R.id.button4);
        image_button_search = findViewById(R.id.image_button_search);
        edit_text_search_value = findViewById(R.id.edit_text_search_value);
        ListView list_view_products = findViewById(R.id.products);

        DatabaseReference products_ref = reference.getProducts();

        image_button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_value = edit_text_search_value.getText().toString();

                products_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        ArrayList<Product> products = new ArrayList<>();
                        for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            if (search_value.isEmpty()) {
                                products.add(product);
                            } else {
                                if (product.getName().contains(search_value)) {
                                    products.add(product);
                                }
                            }

                        }
                        ProductIndexAdapter productIndexAdapter = new ProductIndexAdapter(TrangChu.this, products);
                        list_view_products.setAdapter(productIndexAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });


        bt_ThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChu.this, TrangThongBao.class);
                startActivity(i);
            }
        });
        bt_TTCaNhan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangChu.this, TrangMyProfile.class);
                startActivity(i);
            }
        });
        bt_KhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TrangChu.this,TrangKhuyenMai.class);
                startActivity(i);
            }
        });



        products_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Product> products = new ArrayList<>();
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);

                    Log.d("products", product.toString());
                    products.add(product);
                }
                ProductIndexAdapter productIndexAdapter = new ProductIndexAdapter(TrangChu.this, products);
                list_view_products.setAdapter(productIndexAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        bt_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChu.this, TrangGioHang.class);

                startActivity(intent);
            }
        });
    }
}