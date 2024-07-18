package com.example.finalproject.TimKiem;

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

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

public class Timkiemdacsan extends AppCompatActivity {
    EditText edtTenMonAn, edtIdMonAn;
    Button btnTimKiem;
    ListView lvMonAn;
    Spinner spnVungMien;
    ArrayList<MonAn> dsMonAn = new ArrayList<>();
    AdapterDacSan adapterDacSan;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiemdacsan);
        dbHelper = new DBHelper(getApplicationContext());

        AddControls();
        AddEvents();
        LoadSpinnerData();
        LoadSQLiteData();
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

    private void LoadSQLiteData() {
        try {
            dsMonAn.clear();
            dsMonAn.addAll(dbHelper.getAllMonAn());
            adapterDacSan.notifyDataSetChanged();
            Log.d("DEBUG", "Data loaded from SQLite, size: " + dsMonAn.size());
        } catch (Exception e) {
            Log.e("ERROR", "Error loading data from SQLite: " + e.getMessage());
            // Handle error (e.g., display a message to the user)
        }
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
