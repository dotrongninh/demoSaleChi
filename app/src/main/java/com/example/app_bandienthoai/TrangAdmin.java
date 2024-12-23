package com.example.app_bandienthoai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import models.User;

public class TrangAdmin extends AppCompatActivity {
    Button btUpdateSP, btXemTTKhachHang, btDuyetDonHang,btDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_admin);
        getSupportActionBar().hide();

        btDangXuat = findViewById(R.id.btDANGXUAT);

        btXemTTKhachHang = findViewById(R.id.btXemTTKhachHang);
        btDuyetDonHang =findViewById(R.id.btDuyetDonHang);

        btDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TrangAdmin.this, TrangDangNhap.class);
                startActivity(i);
            }
        });
        btDuyetDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(TrangAdmin.this, TrangDuyetDonHang.class);
                startActivity(i);
            }
        });
        btXemTTKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangAdmin.this, TrangXemThongTinKH.class);
                startActivity(i);
            }
        });

        mapping_client();

        this.btUpdateSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TrangAdmin.this, TrangUpdateSP.class);

                startActivity(i);
            }
        });
    }


    private void mapping_client() {
        this.btUpdateSP = findViewById(R.id.btUpdateSp);
    }
}