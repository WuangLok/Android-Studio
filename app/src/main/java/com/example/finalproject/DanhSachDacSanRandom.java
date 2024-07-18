package com.example.finalproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.TimKiem.MonAn;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DanhSachDacSanRandom extends AppCompatActivity {

    ListView lvDacSan;
    AdapterDacSanRandom customAdapter;
    ArrayList<MonAn> specialties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachdacsanrandom);

        lvDacSan = findViewById(R.id.lvDanhSachDacSan);

        // Load the JSON data
        String json = loadJSONFromAsset();
        if (json != null) {
            List<MonAn> monAnList = parseJSON(json);

            // Shuffle the list for random display
            Collections.shuffle(monAnList, new Random());

            // Set the adapter
            customAdapter = new AdapterDacSanRandom(this, monAnList);
            lvDacSan.setAdapter(customAdapter);
        }
    }

    private String loadJSONFromAsset() {
        try {
            // Change "your_json_file.json" to the actual name of your JSON file in assets
            InputStream is = getAssets().open("dacsan.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private List<MonAn> parseJSON(String json) {
        List<MonAn> monAnList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                MonAn monAn = new MonAn(
                        obj.getInt("id"),
                        obj.getString("tenMonAn"),
                        obj.getString("loaiMonAn"),
                        obj.getString("vungMien"),
                        obj.getString("hinhAnh"),
                        obj.getString("congThuc"),
                        obj.getString("lichSu"),
                        obj.getString("sangTao"),
                        obj.getBoolean("farvorite")
                );
                monAnList.add(monAn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return monAnList;
    }
}
