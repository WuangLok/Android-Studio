package com.example.finalproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DanhSachDacSanRandom extends AppCompatActivity {

    private ListView lvDacSan;
    private AdapterDacSanRandom customAdapter;
    private ArrayList<MonAn> specialties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachdacsanrandom);

        lvDacSan = findViewById(R.id.lvDanhSachDacSan);

        // Load the JSON data
        String json = loadJSONFromAsset();
        if (json != null) {
            try {
                specialties = parseJSON(json);
                // Shuffle the list for random display
                Collections.shuffle(specialties, new Random());

                // Set the adapter
                customAdapter = new AdapterDacSanRandom(this, specialties);
                lvDacSan.setAdapter(customAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String loadJSONFromAsset() {
        try {
            InputStream is = getAssets().open("your_json_file.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private ArrayList<MonAn> parseJSON(String json) throws JSONException {
        ArrayList<MonAn> monAnList = new ArrayList<>();
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
            boolean farvorite = jsonObject.getBoolean("farvorite");

            MonAn monAn = new MonAn(id, tenMonAn, loaiMonAn, vungMien, hinhAnh, congThuc, lichSu, sangTao, farvorite);
            monAnList.add(monAn);
        }
        return monAnList;
    }
}
