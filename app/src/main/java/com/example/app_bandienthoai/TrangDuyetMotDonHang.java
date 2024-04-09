package com.example.app_bandienthoai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapters.OrderAdapter;
import adapters.ProductCheckoutAdapter;
import common.ProductCartOrCheckout;
import models.Order;
import models.Product;

public class TrangDuyetMotDonHang extends AppCompatActivity {
    DatabaseReference usersRef;
    DatabaseReference usersRefOrder;
    TextView txtHoTen ,txtSDT,txtNgayDat,txtDiaChi;
    ListView listView;

    ArrayList <String> product_id_list;
  //  List<Order> orderList;
    List<ProductCartOrCheckout> orderList;
   // OrderAdapter orderAdapter;
    TextView txtTongTien,txtGiamGia,txtThanhTien;
    ProductCheckoutAdapter orderAdapter;
    Button btDuyet,btXoa;
    int ix=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_duyet_mot_don_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtThanhTien =findViewById(R.id.ThanhTien);
        txtTongTien = findViewById(R.id.TongTien);
        txtGiamGia = findViewById(R.id.GiamGia);
        btDuyet = findViewById(R.id.btDuyetDonHang);
        btXoa=findViewById(R.id.btXoaDonHang);
        txtHoTen =findViewById(R.id.textViewSuaTen);
        txtSDT=findViewById(R.id.textViewSDT);
        txtDiaChi=findViewById(R.id.textViewDiaChiMĐ);
        txtNgayDat=findViewById(R.id.textViewNgayĐH);
        Intent i =getIntent();
        String id = i.getStringExtra("id");
        usersRef = FirebaseDatabase.getInstance().getReference("Invoices").child(id);
       listView = findViewById(R.id.list);
       product_id_list=new ArrayList<>();
       orderList =new ArrayList<>();
       orderAdapter =new ProductCheckoutAdapter(TrangDuyetMotDonHang.this,orderList);
        listView.setAdapter(orderAdapter);


       usersRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               txtTongTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class)+snapshot.child("discount").getValue(Long.class))+"đ");
               txtThanhTien.setText(String.valueOf(snapshot.child("total").getValue(Long.class))+"đ");
               txtGiamGia.setText(String.valueOf(snapshot.child("discount").getValue(Long.class))+"đ");
               String user_id = snapshot.child("user_id").getValue(String.class);
               DataSnapshot dateOfBirthSnapshot = snapshot.child("create_at");
                txtNgayDat.setText(String.valueOf( dateOfBirthSnapshot.child("date").getValue(Long.class)) +"/"+
                        String.valueOf( dateOfBirthSnapshot.child("month").getValue(Long.class)) +"/"+
                        String.valueOf( dateOfBirthSnapshot.child("year").getValue(Long.class)));
                txtDiaChi.setText(snapshot.child("address").getValue(String.class));
               DataSnapshot dataSnapshot = snapshot.child("orders");
                              for (DataSnapshot order_child : dataSnapshot.getChildren()){

                                  String  product_id =order_child.child("product_id").getValue(String.class);

                              //    product_id_list.add(product_id);





//                   float price =order_child.child("price").getValue(long.class);
//                  float price_sale =order_child.child("price_sale").getValue(long.class);

                               //   String  product_id =order_child.child("product_id").getValue(String.class);
                                  int quantity =order_child.child("quantity").getValue(Integer.class);
                                  DatabaseReference usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(product_id);
                                  usersRef3.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                                          if(snapshot.exists()) {
                                              Product product = snapshot.getValue(Product.class);
                                              ProductCartOrCheckout productCartOrCheckout = new ProductCartOrCheckout(product, quantity);
                                              orderList.add(productCartOrCheckout);
                                          }
                                          orderAdapter.notifyDataSetChanged();
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError error) {

                                      }
                                  });


        //           Order o =new Order(product_id,quantity,price,price_sale);
//                                  Order o =new Order();
//                                  o = order_child.getValue(Order.class);
//                   orderList.add(o);

               }

//                              for(String x :product_id_list){
//
//                                  DatabaseReference  usersRef3 = FirebaseDatabase.getInstance().getReference("Products").child(x);
//                                  usersRef3.addValueEventListener(new ValueEventListener() {
//                                      @Override
//                                      public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                          if(snapshot.exists())
//                                          {
//                                              ix++;
//
//                                              Product p =snapshot.getValue(Product.class);
//                                            //  Toast.makeText(TrangDuyetMotDonHang.this, p.getName().toString(), Toast.LENGTH_SHORT).show();
//                                              ProductCartOrCheckout productCartOrCheckout =new ProductCartOrCheckout(p,100);
//                                              orderList.add(productCartOrCheckout);
//
//                                          }
//                                          else  Toast.makeText(TrangDuyetMotDonHang.this, "sdfg: "+String.valueOf(ix), Toast.LENGTH_SHORT).show();
//                                          orderAdapter.notifyDataSetChanged();
//                                      }
//
//                                      @Override
//                                      public void onCancelled(@NonNull DatabaseError error) {
//
//                                      }
//                                  });
//
//
//
//
//                              }



     try {
               DatabaseReference usersRef2 = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
               usersRef2.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       txtHoTen.setText(dataSnapshot.child("name").getValue(String.class));
                       txtSDT.setText(dataSnapshot.child("phone").getValue(String.class));

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });}
     catch (NullPointerException e) {
         // Xử lý khi product_id là null
         Log.e("TrangDuyetMotDonHang", "product_id is null: " + e.getMessage());
     }
             //  orderAdapter.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }

       });

//       usersRefOrder = FirebaseDatabase.getInstance().getReference("Invoices").child(id).child("orders");
//       usersRefOrder.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               for (DataSnapshot order_child : snapshot.getChildren()){
//                   float price =order_child.child("price").getValue(float.class);
//                  float price_sale =order_child.child("price_sale").getValue(Float.class);
//                   String product_id =order_child.child("product_id").getValue(String.class);
//                   int quantity =Integer.parseInt( order_child.child("quantity").getValue(String.class));
//                   Order o =new Order(product_id,quantity,price,price_sale);
//                   orderList.add(o);
//                   Toast.makeText(getApplicationContext(), "sô lương: "+String.valueOf(ix), Toast.LENGTH_SHORT).show();
//               }
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       });
        btDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  usersRef.child("is_validate").setValue(true);
            }
        });
        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(TrangDuyetMotDonHang.this, TrangDuyetDonHang.class);
                intent.putExtra("delete",true);
                intent.putExtra("id_invoice",id);
                startActivity(intent);
                finish();
    //usersRef.removeValue();




            }
        });

    }
}