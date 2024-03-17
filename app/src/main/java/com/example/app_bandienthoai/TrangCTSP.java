package com.example.app_bandienthoai;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import models.Product;
import reference.Reference;

public class TrangCTSP extends AppCompatActivity {
    private final Reference reference = new Reference();

    private final DatabaseReference products_ref = reference.getProducts();

    private ImageView image_view_product_image;

    TextView text_view_product_name, text_view_price, text_view_price_sale, text_view_product_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_ctsp);

        mapping_client();

        getSupportActionBar().hide();

        String product_id = getIntent().getStringExtra("product_id");

        products_ref.child(product_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data_snapshot) {
                Product product = data_snapshot.getValue(Product.class);

                text_view_product_name.setText(product.getName());
                text_view_price.setText(Float.toString(product.getPrice()));
                text_view_price_sale.setText(Float.toString(product.getPrice() - product.getPrice_sale()));
                text_view_product_description.setText(product.getDescription());
                Glide.with(TrangCTSP.this).load(product.getImage()).into(image_view_product_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void mapping_client() {
        image_view_product_image = findViewById(R.id.image_view_product_image);
        text_view_product_name = findViewById(R.id.text_view_product_name);
        text_view_price = findViewById(R.id.text_view_price);
        text_view_price_sale = findViewById(R.id.text_view_price_sale);
        text_view_product_description = findViewById(R.id.text_view_product_description);
    }
}