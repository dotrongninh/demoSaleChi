package com.example.app_bandienthoai;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

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
import java.util.Date;
import java.util.List;

import adapters.UserAdapter;
import adapters.User_NotificationAdapter;
import models.User;
import models.User_Notification;
import models.Voucher;

public class TrangThongBao extends AppCompatActivity {
    DatabaseReference usersRef;

    List<User_Notification> user_notifications;
    User_NotificationAdapter adapter;
    SharedPreferences sharedPreferences;
    ListView listViewNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_thong_bao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences= getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");
        getSupportActionBar().hide();
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(myId).child("user_notifications");
        listViewNotifications=findViewById(R.id.thongbaocanhan);

        user_notifications=new ArrayList<>();
        adapter= new User_NotificationAdapter(this,R.layout.item_thongbao,user_notifications);
        listViewNotifications.setAdapter(adapter);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_notifications.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    String content = snapshot.child("content").getValue(String.class);

                    String Name = snapshot.child("name").getValue(String.class);
                    String id = snapshot.child("id").getValue(String.class);
                    int day = Math.toIntExact(snapshot.child("date").child("date").getValue(Long.class));
                    int month = Math.toIntExact(snapshot.child("date").child("month").getValue(Long.class));
                    int year = Math.toIntExact(snapshot.child("date").child("year").getValue(Long.class));
                    Date date = new Date(year,month,day);
                    String id1 = snapshot.getKey();
                    ArrayList<User_Notification> arrayList =new ArrayList<>();

                    User_Notification user = new User_Notification(id,Name,content,date);
                    // User user = new User("1",Name,Email,PassWord,date,SDT,arrayList);
                    user_notifications.add(0,user);

                    //User user = dataSnapshot.getValue(User.class);
                    // userList.add(user);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}