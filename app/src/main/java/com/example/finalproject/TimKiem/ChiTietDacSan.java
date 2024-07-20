package com.example.finalproject.TimKiem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

public class ChiTietDacSan extends AppCompatActivity {

    TextView txtTenMonAn, txtLoaiMonAn, txtVungMien, txtCongThuc, txtLichSu, txtSangTao;
    ImageView imgHinhAnh;
    ImageView YeuThich;
    boolean isFavorite;
    int monAnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dac_san);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        findViewById(R.id.btnChiaSe).setOnClickListener(v -> shareDish());

        txtTenMonAn = findViewById(R.id.txtTenMonAn);
        txtLoaiMonAn = findViewById(R.id.txtLoaiMonAn);
        txtVungMien = findViewById(R.id.txtVungMien);
        txtCongThuc = findViewById(R.id.txtCongThuc);
        txtLichSu = findViewById(R.id.txtLichSu);
        txtSangTao = findViewById(R.id.txtSangTao);
        imgHinhAnh = findViewById(R.id.imgHinhAnh);
        YeuThich = findViewById(R.id.YeuThich);

        Intent intent = getIntent();
        if (intent != null) {
            monAnId = intent.getIntExtra("id", -1);
            txtTenMonAn.setText(intent.getStringExtra("tenMonAn"));
            txtLoaiMonAn.setText(intent.getStringExtra("loaiMonAn"));
            txtVungMien.setText(intent.getStringExtra("vungMien"));
            txtCongThuc.setText(intent.getStringExtra("congThuc"));
            txtLichSu.setText(intent.getStringExtra("lichSu"));
            txtSangTao.setText(intent.getStringExtra("sangTao"));

            String hinhAnh = intent.getStringExtra("hinhAnh");
            int resID = getResources().getIdentifier(hinhAnh, "drawable", getPackageName());
            imgHinhAnh.setImageResource(resID);

            isFavorite = intent.getBooleanExtra("favorite", false);
            YeuThich.setImageResource(isFavorite ? R.drawable.redheart : R.drawable.emptyheart);


        }
    }

    private void shareDish() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Tên món ăn: " + txtTenMonAn.getText();
        String shareSub = "Món Ăn: " + txtVungMien.getText() + ", Với Lịch Sử: " + txtLichSu.getText();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }

    private void toggleFavorite() {
        if (monAnId != -1) {
            isFavorite = !isFavorite;

            DBHelper dbHelper = new DBHelper(ChiTietDacSan.this);
            dbHelper.updateFavorite(monAnId, isFavorite);

            YeuThich.setImageResource(isFavorite ? R.drawable.redheart : R.drawable.emptyheart);

            Intent intent = getIntent();
            intent.putExtra("favorite", isFavorite);
            setResult(RESULT_OK, intent);
        }
    }
}
