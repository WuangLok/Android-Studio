package com.example.finalproject.TimKiem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

public class ChiTietDacSan extends AppCompatActivity {

    TextView txtTenMonAn, txtLoaiMonAn, txtVungMien, txtCongThuc, txtLichSu, txtSangTao;
    ImageView imgHinhAnh;
    Button btnChiaSe, btnTroLai,btnYeuThich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dac_san);

        btnTroLai = findViewById(R.id.btnBack);
        btnChiaSe = findViewById(R.id.btnChiaSe);
        txtTenMonAn = findViewById(R.id.txtTenMonAn);
        txtLoaiMonAn = findViewById(R.id.txtLoaiMonAn);
        txtVungMien = findViewById(R.id.txtVungMien);
        txtCongThuc = findViewById(R.id.txtCongThuc);
        txtLichSu = findViewById(R.id.txtLichSu);
        txtSangTao = findViewById(R.id.txtSangTao);
        imgHinhAnh = findViewById(R.id.imgHinhAnh);
        btnYeuThich = findViewById(R.id.btnYeuThich);

        Intent intent = getIntent();
        if (intent != null) {
            txtTenMonAn.setText(intent.getStringExtra("tenMonAn"));
            txtLoaiMonAn.setText(intent.getStringExtra("loaiMonAn"));
            txtVungMien.setText(intent.getStringExtra("vungMien"));
            txtCongThuc.setText(intent.getStringExtra("congThuc"));
            txtLichSu.setText(intent.getStringExtra("lichSu"));
            txtSangTao.setText(intent.getStringExtra("sangTao"));


            String hinhAnh = intent.getStringExtra("hinhAnh");
            int resID = getResources().getIdentifier(hinhAnh, "drawable", getPackageName());
            imgHinhAnh.setImageResource(resID);

            btnChiaSe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareBody = "Tên món ăn: " + txtTenMonAn.getText();
                    String shareSub = "Món Ăn: " + txtVungMien.getText() + ", Với Lịch Sử: " + txtLichSu.getText();
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    startActivity(Intent.createChooser(shareIntent, "Share using"));
                }
            });
            btnYeuThich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the ID of the dish
                    int monAnId = getIntent().getIntExtra("id", -1);
                    if (monAnId != -1) {
                        // Get the current favorite status and toggle it
                        boolean isFavorite = getIntent().getBooleanExtra("favorite", false);
                        boolean newFavoriteStatus = !isFavorite;

                        // Update the favorite status in the database
                        DBHelper dbHelper = new DBHelper(ChiTietDacSan.this);
                        dbHelper.updateFavorite(monAnId, newFavoriteStatus);

                        // Update the button text or icon based on the new status
                        btnYeuThich.setText(newFavoriteStatus ? "Đã yêu thích" : "Yêu thích");

                        // Optionally, update the Intent extra to reflect the new status
                        Intent intent = getIntent();
                        intent.putExtra("favorite", newFavoriteStatus);
                        setResult(RESULT_OK, intent);
                    }
                }
            });


            btnTroLai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
