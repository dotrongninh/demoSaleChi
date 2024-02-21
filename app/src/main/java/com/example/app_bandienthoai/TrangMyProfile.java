package com.example.app_bandienthoai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrangMyProfile extends AppCompatActivity {
    DatabaseReference usersRef;
    int frontId=-1;
    SharedPreferences sharedPreferences;
    EditText edHoTen,edSDT,edNgaySinh;
    TextView txtEmail,txtProFileName;
    Button btEditThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_trang_my_profile);
        sharedPreferences= getSharedPreferences("com.example.sharedprerences", Context.MODE_PRIVATE);
        String myId = sharedPreferences.getString("id", "-1");
        usersRef = FirebaseDatabase.getInstance().getReference("Users").child(myId);
        edHoTen=findViewById(R.id.textViewSuaTen);
        edSDT=findViewById(R.id.textViewSDT);
       txtEmail=findViewById(R.id.textViewEmail);
       edSDT = findViewById(R.id.textViewSDT);
       txtProFileName=findViewById(R.id.profileName);
       edNgaySinh =findViewById(R.id.textViewNgaySinh);
       btEditThongTin=findViewById(R.id.btEditThongTin);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    frontId = dataSnapshot.child("email").getValue(String.class).indexOf('@');
                    DataSnapshot dateOfBirthSnapshot = dataSnapshot.child("date_of_birth");
                    edHoTen.setText("Họ tên: "+ dataSnapshot.child("name").getValue(String.class));
                    txtEmail.setText("Email: "+ dataSnapshot.child("email").getValue(String.class));
                    edSDT.setText("SDT: "+ dataSnapshot.child("phone").getValue(String.class));
                    edNgaySinh.setText("Ngày sinh: "+ String.valueOf( dateOfBirthSnapshot.child("date").getValue(Long.class)) +"/"+
                            String.valueOf( dateOfBirthSnapshot.child("month").getValue(Long.class)) +"/"+
                            String.valueOf( dateOfBirthSnapshot.child("year").getValue(Long.class))
                    );


                    if(frontId>=0)
                        txtProFileName.setText(dataSnapshot.child("email").getValue(String.class).substring(0,frontId));
                    else
                        txtProFileName.setText(dataSnapshot.child("email").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

}