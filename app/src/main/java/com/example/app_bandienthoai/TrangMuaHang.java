package com.example.app_bandienthoai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import adapters.ProductCheckoutAdapter;
import common.ProductCartOrCheckout;
import models.Product;
import reference.Reference;

public class TrangMuaHang extends AppCompatActivity {
    final Reference reference = new Reference();

    final DatabaseReference products_ref = reference.getProducts();

    ArrayList<ProductCartOrCheckout> _products_checkout = new ArrayList<>();

    double total_price = 0;

    ListView list_view_products;

    ProductCheckoutAdapter adapter;

    TextView text_view_sub_total_price, text_view_total_price;

    EditText edit_text_address;

    Button button_order;

    ImageButton image_button_home, image_button_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_mua_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mapping_client();

        Intent intent = getIntent();

        HashMap<String, Integer> _h_products = (HashMap<String, Integer>) intent.getSerializableExtra("products");

        assert _h_products != null;
        for (String key : _h_products.keySet()) {
            int _quantity = _h_products.get(key);

            products_ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Product product = snapshot.getValue(Product.class);

                    total_price += product.getPrice() * _quantity;

                    _products_checkout.add(new ProductCartOrCheckout(product, _quantity));

                    adapter.notifyDataSetChanged();

                    text_view_sub_total_price.setText(Double.toString(total_price) + " đ");

                    text_view_total_price.setText(Double.toString(total_price) + " đ");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if (_products_checkout != null) {

            adapter = new ProductCheckoutAdapter(TrangMuaHang.this, _products_checkout);

            list_view_products.setAdapter(adapter);

        }

        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle_checkout();
            }
        });

        image_button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangMuaHang.this, TrangChu.class);
                startActivity(intent);
            }
        });

        image_button_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangMuaHang.this, TrangGioHang.class);
                startActivity(intent);
            }
        });
    }


    private void mapping_client() {
        this.list_view_products = findViewById(R.id.list_view_products);
        this.text_view_sub_total_price = findViewById(R.id.TongTien);
        this.text_view_total_price = findViewById(R.id.ThanhTien);
        this.edit_text_address = findViewById(R.id.address);
        this.button_order = findViewById(R.id.order);
        this.image_button_home = findViewById(R.id.button1);
        this.image_button_cart = findViewById(R.id.button4);
    }

    private void handle_checkout() {
        String address = edit_text_address.getText().toString();

        if (address.isEmpty()) {
            Toast toast = Toast.makeText(TrangMuaHang.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            //do something

        }
    }
}