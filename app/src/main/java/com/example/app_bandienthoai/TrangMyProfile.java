package com.example.app_bandienthoai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class TrangMyProfile extends AppCompatActivity {
    DatabaseReference usersRef;
    int frontId=-1;
    SharedPreferences sharedPreferences;
    EditText edHoTen,edSDT,edNgaySinh;
    TextView txtEmail,txtProFileName;
    ImageButton btEditThongTin;
    Button btDonHangDaMua;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
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
       btDonHangDaMua = findViewById(R.id.btDonHangDaMua);

       btDonHangDaMua.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i =new Intent(TrangMyProfile.this,TrangXemDHdamua.class);
               startActivity(i);
           }
       });



    btEditThongTin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showCustomDialog();
        }
    });
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
    private void showCustomDialog() {
        // Tạo đối tượng Dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);

        // Ánh xạ các thành phần trong dialog
        EditText editTextName = dialog.findViewById(R.id.editTextName);
        EditText editTextPhoneNumber = dialog.findViewById(R.id.editTextPhoneNumber);
        TextView textViewEmail = dialog.findViewById(R.id.textViewEmail);
        EditText editTextNgaySinh = dialog.findViewById(R.id.editTextNgaySinh);
        Button buttonSave = dialog.findViewById(R.id.buttonSave);
        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    frontId = dataSnapshot.child("email").getValue(String.class).indexOf('@');
                    DataSnapshot dateOfBirthSnapshot = dataSnapshot.child("date_of_birth");
                    editTextName.setText( dataSnapshot.child("name").getValue(String.class));
                    textViewEmail.setText( dataSnapshot.child("email").getValue(String.class));
                    editTextPhoneNumber.setText( dataSnapshot.child("phone").getValue(String.class));
                    editTextNgaySinh.setText(String.valueOf( dateOfBirthSnapshot.child("date").getValue(Long.class)) +"/"+
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


        // Thiết lập sự kiện khi nhấn vào nút "Lưu"
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý lưu dữ liệu
                usersRef.child("name").setValue(editTextName.getText().toString());
                usersRef.child("phone").setValue(editTextPhoneNumber.getText().toString());
                String ngaySinh = editTextNgaySinh.getText().toString();
                Date date = new Date(Integer.parseInt(ngaySinh.substring(6,10)),Integer.parseInt(ngaySinh.substring(3,5)),Integer.parseInt(ngaySinh.substring(0,2)));

                usersRef.child("date_of_birth").setValue(date);

                edHoTen.setText("Họ tên: "+ editTextName.getText().toString());

                edSDT.setText("SDT: "+ editTextPhoneNumber.getText().toString());
                edNgaySinh.setText("Ngày sinh: "+ editTextNgaySinh.getText().toString()
                );

                // Sau khi lưu xong, đóng dialog
                dialog.dismiss();

            }
        });

        // Thiết lập sự kiện khi nhấn vào nút "Hủy"
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đóng dialog
                dialog.dismiss();
            }
        });

        // Hiển thị dialog
        dialog.show();
    }

}