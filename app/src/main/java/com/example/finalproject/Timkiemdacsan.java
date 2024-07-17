package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Timkiemdacsan extends AppCompatActivity {
    EditText edtTenMonAn, edtIdMonAn;
    Button btnTimKiem;
    ListView lvMonAn;
    Spinner spnVungMien;
    ArrayList<MonAn> dsMonAn = new ArrayList<>();
    AdapterDacSan adapterDacSan ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiemdacsan);
        AddControls();
        AddEvents();
        LoadSpinnerData();
        LoadJsonData();

    }

    private void AddControls() {
        edtTenMonAn = findViewById(R.id.EdtTenMonAn);
        edtIdMonAn = findViewById(R.id.EdtIdMonAn);
        btnTimKiem = findViewById(R.id.btnTim);
        lvMonAn = findViewById(R.id.lvDanhSach);
        spnVungMien = findViewById(R.id.SpinnerVungMien);

        adapterDacSan = new AdapterDacSan(this, dsMonAn);
        lvMonAn.setAdapter(adapterDacSan);
    }

    private void AddEvents() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMonAn = edtTenMonAn.getText().toString().trim();
                String idMonAn = edtIdMonAn.getText().toString().trim();
                String vungMien = spnVungMien.getSelectedItem().toString();
                TimKiemMonAn(tenMonAn, idMonAn, vungMien);
            }

        });
        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MonAn selectedMonAn = dsMonAn.get(position);
                Intent intent = new Intent(Timkiemdacsan.this, ChiTietDacSan.class);
                intent.putExtra("id", selectedMonAn.getId());
                intent.putExtra("tenMonAn", selectedMonAn.getTenMonAn());
                intent.putExtra("loaiMonAn", selectedMonAn.getLoaiMonAn());
                intent.putExtra("vungMien", selectedMonAn.getVungMien());
                intent.putExtra("hinhAnh", selectedMonAn.getHinhAnh());
                intent.putExtra("congThuc", selectedMonAn.getCongThuc());
                intent.putExtra("lichSu", selectedMonAn.getLichSu());
                intent.putExtra("sangTao", selectedMonAn.getSangTao());
                intent.putExtra("favorite", selectedMonAn.isFarvorite());
                startActivity(intent);
            }
        });

        spnVungMien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vungMien = spnVungMien.getSelectedItem().toString();
                FilterByRegion(vungMien);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void LoadSpinnerData() {
        List<String> regions = new ArrayList<>();
        regions.add("Tất cả");
        regions.add("Bắc");
        regions.add("Trung");
        regions.add("Nam");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVungMien.setAdapter(spinnerAdapter);
    }

    private void LoadJsonData() {
        String jsonStr = loadJSONFromAsset("dacsan.json");
        if (jsonStr != null) {
            ArrayList<MonAn> list = parseJsonToDacSanList(jsonStr);
            if (list != null) {
                dsMonAn.addAll(list);
                adapterDacSan.notifyDataSetChanged();
                Log.d("DEBUG", "Data loaded, size: " + dsMonAn.size());
            } else {
                Log.d("DEBUG", "Parsed list is null");
            }
        } else {
            Log.d("DEBUG", "JSON string is null");
        }
    }


    private String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private ArrayList<MonAn> parseJsonToDacSanList(String jsonStr) {
        ArrayList<MonAn> dacSanList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                MonAn dacSan = new MonAn(
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
                dacSanList.add(dacSan);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dacSanList;
    }

    private void TimKiemMonAn(String tenMonAn, String idMonAn, String vungMien) {
        ArrayList<MonAn> filteredList = new ArrayList<>();
        for (MonAn monAn : dsMonAn) {
            boolean matchesTen = tenMonAn.isEmpty() || monAn.getTenMonAn().toLowerCase().contains(tenMonAn.toLowerCase());
            boolean matchesId = idMonAn.isEmpty() || String.valueOf(monAn.getId()).equals(idMonAn);
            boolean matchesRegion = vungMien.equals("Tất cả") || monAn.getVungMien().equals(vungMien);

            if (matchesTen && matchesId && matchesRegion) {
                filteredList.add(monAn);
            }
        }

        adapterDacSan.clear();
        adapterDacSan.addAll(filteredList);
        adapterDacSan.notifyDataSetChanged();
    }

    private void FilterByRegion(String vungMien) {
        ArrayList<MonAn> filteredList = new ArrayList<>();
        for (MonAn monAn : dsMonAn) {
            if (vungMien.equals("Tất cả") || monAn.getVungMien().equals(vungMien)) {
                filteredList.add(monAn);
            }
        }

        adapterDacSan.clear();
        adapterDacSan.addAll(filteredList);
        adapterDacSan.notifyDataSetChanged();
    }
}
