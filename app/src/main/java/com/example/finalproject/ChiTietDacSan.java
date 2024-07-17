package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietDacSan extends AppCompatActivity {

    TextView txtTenMonAn, txtLoaiMonAn, txtVungMien, txtCongThuc, txtLichSu, txtSangTao;
    ImageView imgHinhAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dac_san);

        txtTenMonAn = findViewById(R.id.txtTenMonAn);
        txtLoaiMonAn = findViewById(R.id.txtLoaiMonAn);
        txtVungMien = findViewById(R.id.txtVungMien);
        txtCongThuc = findViewById(R.id.txtCongThuc);
        txtLichSu = findViewById(R.id.txtLichSu);
        txtSangTao = findViewById(R.id.txtSangTao);
        imgHinhAnh = findViewById(R.id.imgHinhAnh);

        Intent intent = getIntent();
        if (intent != null) {
            txtTenMonAn.setText(intent.getStringExtra("tenMonAn"));
            txtLoaiMonAn.setText(intent.getStringExtra("loaiMonAn"));
            txtVungMien.setText(intent.getStringExtra("vungMien"));
            txtCongThuc.setText(intent.getStringExtra("congThuc"));
            txtLichSu.setText(intent.getStringExtra("lichSu"));
            txtSangTao.setText(intent.getStringExtra("sangTao"));

            String hinhAnh = intent.getStringExtra("hinhAnh");

        }
    }
}
