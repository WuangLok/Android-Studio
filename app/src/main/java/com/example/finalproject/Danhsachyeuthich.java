package com.example.finalproject;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.AdapterYeuThich;
import com.example.finalproject.YeuThich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Danhsachyeuthich extends AppCompatActivity {

    ListView lvYeuThich;
    AdapterYeuThich customAdapter;
    ArrayList<YeuThich> specialties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachyeuthich);

        lvYeuThich = findViewById(R.id.lvDanhSachYeuThich);
        specialties = new ArrayList<>();

        // Load data from JSON file
        loadSpecialtiesFromJson();

        // Setup adapter
        customAdapter = new AdapterYeuThich(this, R.layout.customlistview_yeuthich, specialties);
        lvYeuThich.setAdapter(customAdapter);

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

                // Only add to list if favorite is true
                if (favorite) {
                    YeuThich yeuThich = new YeuThich(id, tenMonAn, loaiMonAn, vungMien, hinhAnh, congThuc, lichSu, sangTao, favorite);
                    specialties.add(yeuThich);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
