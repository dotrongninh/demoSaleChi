package com.example.app_bandienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

    private final Reference reference = new Reference();
    private final ProductService product_service = new ProductService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

        ListView list_view_products = findViewById(R.id.products);


        DatabaseReference products_ref = reference.getProducts();
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
    }
}