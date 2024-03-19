package com.example.app_bandienthoai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapters.UserAdapter;
import models.User;
import models.Voucher;

public class TrangXemThongTinKH extends AppCompatActivity {
    DatabaseReference usersRef;
    List<String> Emaillist;
    List<User> userList;
    UserAdapter adapter;
    ListView listViewUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_xem_thong_tin_kh);
        getSupportActionBar().hide();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        listViewUsers = findViewById(R.id.listViewKH);

        // Khởi tạo danh sách học sinh và adapter
        userList = new ArrayList<>();
        adapter = new UserAdapter(this, R.layout.item_kh_admin, userList);
        listViewUsers.setAdapter(adapter);
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TrangXemThongTinKH.this, userList.get(i).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TrangXemThongTinKH.this, TrangXemThongTinMotKH.class);
                intent.putExtra("id",userList.get(i).getId());
                startActivity(intent);
            }
        });
        // Lắng nghe sự thay đổi trên Firebase Database
        usersRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa dữ liệu cũ trước khi cập nhật mới

                userList.clear();

                // Duyệt qua tất cả các học sinh trong dataSnapshot
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("email").getValue(String.class).equals("-1"))
                        continue;
                    // Lấy thông tin của học sinh và thêm vào danh sách

                    String Email = snapshot.child("email").getValue(String.class);
                    String SDT = snapshot.child("phone").getValue(String.class);
                    String Name = snapshot.child("name").getValue(String.class);
                    String PassWord = snapshot.child("password").getValue(String.class);
                    int day = Math.toIntExact(snapshot.child("date_of_birth").child("date").getValue(Long.class));
                    int month = Math.toIntExact(snapshot.child("date_of_birth").child("month").getValue(Long.class));
                    int year = Math.toIntExact(snapshot.child("date_of_birth").child("year").getValue(Long.class));
                  Date date = new Date(year,month,day);
                    String id = snapshot.getKey();
                    ArrayList<Voucher> arrayList =new ArrayList<>();
                    Voucher voucher=new Voucher("1","New User","Chao mung ban moi","image",100f,0f);
                    arrayList.add(voucher);
                    User user = new User(id,Name,Email,PassWord,date,SDT,arrayList);
                   // User user = new User("1",Name,Email,PassWord,date,SDT,arrayList);
                    userList.add(user);

                    //User user = dataSnapshot.getValue(User.class);
                   // userList.add(user);





                }
                // Cập nhật adapter khi có dữ liệu mới
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });


    }

    }


