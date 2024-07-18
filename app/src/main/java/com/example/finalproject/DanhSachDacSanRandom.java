// DanhSachDacSanRandom.java
package com.example.finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.TimKiem.MonAn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class DanhSachDacSanRandom extends AppCompatActivity {

    ListView lvDacSan;
    AdapterDacSanRandom customAdapter;
    ArrayList<MonAn> specialties;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customlv_hiendacsanngaunhien);

        lvDacSan = findViewById(R.id.lvDanhSachDacSan);
        specialties = new ArrayList<>();

        // Load data from JSON file
        loadSpecialtiesFromJson();

        // Shuffle the list to randomize the order
        Collections.shuffle(specialties);

        // Setup adapter
        customAdapter = new AdapterDacSanRandom(this, specialties);
        lvDacSan.setAdapter(customAdapter);
    }

    private void loadSpecialtiesFromJson() {
        String json;
        try {
            // Load JSON file from assets
            InputStream is = getAssets().open("dacsan.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            // Parse JSON data
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String tenMonAn = jsonObject.getString("tenMonAn");
                String loaiMonAn = jsonObject.getString("loaiMonAn");
                String vungMien = jsonObject.getString("vungMien");
                String hinhAnh = jsonObject.getString("hinhAnh");
                String congThuc = jsonObject.getString("congThuc");
                String lichSu = jsonObject.getString("lichSu");
                String sangTao = jsonObject.getString("sangTao");
                boolean favorite = jsonObject.getBoolean("farvorite");

                MonAn monAn = new MonAn(id, tenMonAn, loaiMonAn, vungMien, hinhAnh, congThuc, lichSu, sangTao, favorite);
                specialties.add(monAn);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
