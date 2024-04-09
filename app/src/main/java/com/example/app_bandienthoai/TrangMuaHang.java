package com.example.app_bandienthoai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import adapters.VoucherCheckoutAdapter;
import common.ProductCartOrCheckout;
import models.Invoice;
import models.Order;
import models.Product;
import models.User;
import models.Voucher;
import reference.Reference;

public class TrangMuaHang extends AppCompatActivity {
    final Reference reference = new Reference();

    final DatabaseReference products_ref = reference.getProducts();

    final DatabaseReference invoices_ref = reference.getInvoices();

    final DatabaseReference users_ref = reference.getUsers();

    ArrayList<ProductCartOrCheckout> _products_checkout = new ArrayList<>();

    double total_price = 0;

    ListView list_view_products, list_view_vouchers;

    ProductCheckoutAdapter adapter;

    TextView text_view_sub_total_price, text_view_total_price;

    EditText edit_text_address;

    Button button_order;

    ImageButton image_button_home, image_button_cart;

    HashMap<String, Product> products = new HashMap<>();

    boolean is_from_cart;


    ArrayList<Voucher> vouchers = new ArrayList<Voucher>();

    VoucherCheckoutAdapter adapter_voucher;


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

        is_from_cart = intent.getBooleanExtra("is_from_cart", false);

        assert _h_products != null;
        for (String key : _h_products.keySet()) {
            int _quantity = _h_products.get(key);

            products_ref.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("A", "A");
                    Product product = snapshot.getValue(Product.class);

                    total_price += product.getPrice() * _quantity;

                    products.put(key, product);

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

        adapter_voucher = new VoucherCheckoutAdapter(TrangMuaHang.this, vouchers, new HandleCheck());

        SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

        String user_id = sharedpreferences.getString("id", "");

        users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null && user.getVouchers() != null) {
                    for (Voucher _v : user.getVouchers()) {
                        if (total_price > _v.getCondition()) {
                            vouchers.add(_v);
                        }
                    }
                    adapter_voucher = new VoucherCheckoutAdapter(TrangMuaHang.this, vouchers, new HandleCheck());

                    list_view_vouchers.setAdapter(adapter_voucher);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        this.list_view_vouchers = findViewById(R.id.vouchers);
    }

    private void handle_checkout() {
        String address = edit_text_address.getText().toString();

        if (address.isEmpty()) {
            Toast toast = Toast.makeText(TrangMuaHang.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String new_invoice_id = invoices_ref.push().getKey();

            ArrayList<Order> orders = new ArrayList<Order>();

            float total_price = 0;

            SharedPreferences sharedpreferences = getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);

            String user_id = sharedpreferences.getString("id", "");

            for (ProductCartOrCheckout _p : _products_checkout) {

                Order order = new Order(_p.product.getId(), _p.quantity, _p.quantity * _p.product.getPrice(), 0);

                total_price += _p.quantity * _p.product.getPrice();

                orders.add(order);
            }

            float discount = (adapter_voucher != null && adapter_voucher.position_selected != null)
                    ? vouchers.get(adapter_voucher.position_selected).getValue()
                    : 0;

            Invoice new_invoice = new Invoice(new_invoice_id, user_id, address, total_price, discount, orders);

            users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    if (adapter_voucher.position_selected != null) {
                        vouchers.remove(vouchers.get(Integer.valueOf(adapter_voucher.position_selected)));
                    }
                    Log.d("leng", "onDataChange: " + vouchers.size());


                    users_ref.child(user.getId()).child("vouchers").setValue(vouchers);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            invoices_ref.child(new_invoice_id).setValue(new_invoice);

            if (is_from_cart) {
                users_ref.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        for (ProductCartOrCheckout _p : _products_checkout) {
                            user.delete_product(_p.product.getId());
                        }

                        users_ref.child(user.getId()).child("cart").setValue(user.getCart());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            Toast.makeText(this, "Ordered Successfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(TrangMuaHang.this, TrangChu.class);

            startActivity(intent);
        }
    }

    public class HandleCheck {

        public HandleCheck() {

        }

        public void handle_check(Integer position, Integer position_selected) {
            if (position_selected == null || !position_selected.equals(position)) {
                adapter_voucher.position_selected = position;
                adapter_voucher.notifyDataSetChanged();
                text_view_total_price.setText(Double.toString(total_price - vouchers.get(position).getValue()) + " đ");
            } else {
                adapter_voucher.position_selected = null;
                adapter_voucher.notifyDataSetChanged();
                text_view_total_price.setText(Double.toString(total_price) + " đ");
            }
        }
    }
}