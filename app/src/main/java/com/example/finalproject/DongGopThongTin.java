package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DongGopThongTin extends AppCompatActivity {

    private Button btnSubmit;
    private Button btnXemDanhSach;
    private RecyclerView recyclerView;
    private List<Speciality> specialityList;
    private SpecialityAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_gop_thong_tin);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnXemDanhSach = findViewById(R.id.btnXem);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        specialityList = new ArrayList<>();
        adapter = new SpecialityAdapter(this, specialityList);
        recyclerView.setAdapter(adapter);

        loadSpecialitiesFromJson();

        btnSubmit.setOnClickListener(view -> showCustomDialog());

        btnXemDanhSach.setOnClickListener(view -> {
            if (!specialityList.isEmpty()) {
                StringBuilder details = new StringBuilder();
                for (Speciality speciality : specialityList) {
                    details.append("Tên: ").append(speciality.getTenMonAn()).append("\n")
                            .append("Hình ảnh: ").append(speciality.getHinhAnh()).append("\n")
                            .append("Vùng miền: ").append(speciality.getVungMien()).append("\n")
                            .append("Lịch sử: ").append(speciality.getlichsu()).append("\n")
                            .append("Sáng tạo: ").append(speciality.getsangtao()).append("\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DongGopThongTin.this);
                builder.setTitle("Danh sách món ăn");
                builder.setMessage(details.toString());
                builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(DongGopThongTin.this, "Danh sách trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_speciality, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etImage = dialogView.findViewById(R.id.etImage);
        EditText etRegion = dialogView.findViewById(R.id.etRegion);
        EditText etLichsu = dialogView.findViewById(R.id.etLichsu);
        EditText etSangtao = dialogView.findViewById(R.id.etSangtao);
        Button btnSubmit1 = dialogView.findViewById(R.id.btnSubmit1);

        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        btnSubmit1.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String image = etImage.getText().toString();
            String region = etRegion.getText().toString();
            String lichsu = etLichsu.getText().toString();
            String sangtao = etSangtao.getText().toString();

            if (name.isEmpty() || image.isEmpty() || region.isEmpty() || lichsu.isEmpty() || sangtao.isEmpty()) {
                Toast.makeText(DongGopThongTin.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Speciality newSpeciality = new Speciality(
                        specialityList.size() + 1,
                        name,
                        region,
                        image,
                        lichsu,
                        sangtao,
                        false
                );
                specialityList.add(newSpeciality);
                adapter.notifyItemInserted(specialityList.size() - 1);
                Toast.makeText(DongGopThongTin.this, "Đã đóng góp thành công!", Toast.LENGTH_SHORT).show();

                if (specialityList.size() > 0) {
                    btnXemDanhSach.setVisibility(View.VISIBLE);
                }

                dialog.dismiss();
            }
        });
    }

    private void loadSpecialitiesFromJson() {
        String json;
        try {
            InputStream is = getAssets().open("dacsan.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = jsonObject.getInt("id");
                String tenMonAn = jsonObject.getString("tenMonAn");
                String vungMien = jsonObject.getString("vungMien");
                String hinhAnh = jsonObject.getString("hinhAnh");
                String lichsu = jsonObject.getString("lichSu");
                String sangtao = jsonObject.getString("sangTao");
                boolean favorite = false;

                Speciality speciality = new Speciality(id, tenMonAn, vungMien, hinhAnh, lichsu, sangtao, favorite);
                specialityList.add(speciality);
            }
            adapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading specialties", Toast.LENGTH_SHORT).show();
        }
    }
}
