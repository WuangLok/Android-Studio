package com.example.finalproject.TimKiem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimkiemdacsanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimkiemdacsanFragment extends Fragment {

    private EditText edtTenMonAn;
    private Button btnTimKiem;
    private ListView lvMonAn;
    private Spinner spnVungMien, spnLoaiMonAn;
    private ArrayList<MonAn> dsMonAn = new ArrayList<>();
    private AdapterDacSan adapterDacSan;
    private DBHelper dbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimkiemdacsanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimkiemdacsanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimkiemdacsanFragment newInstance(String param1, String param2) {
        TimkiemdacsanFragment fragment = new TimkiemdacsanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timkiemdacsan, container, false);
        dbHelper = new DBHelper(getContext());

        edtTenMonAn = view.findViewById(R.id.EdtTenMonAn);
        btnTimKiem = view.findViewById(R.id.btnTim);
        lvMonAn = view.findViewById(R.id.lvDanhSach);
        spnVungMien = view.findViewById(R.id.SpinnerVungMien);
        spnLoaiMonAn = view.findViewById(R.id.SpinnerLoaiMonAn);

        adapterDacSan = new AdapterDacSan(getContext(), dsMonAn);
        lvMonAn.setAdapter(adapterDacSan);

        LoadSpinnerData();
        LoadSQLiteData();

        btnTimKiem.setOnClickListener(v -> {
            String tenMonAn = edtTenMonAn.getText().toString().trim();
            String vungMien = spnVungMien.getSelectedItem().toString();
            String loaiMonAn = spnLoaiMonAn.getSelectedItem().toString();
            TimKiemMonAn(tenMonAn, vungMien, loaiMonAn);
        });

        lvMonAn.setOnItemClickListener((parent, view1, position, id) -> {
            MonAn selectedMonAn = dsMonAn.get(position);
            Intent intent = new Intent(getActivity(), ChiTietDacSan.class);
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

        spnLoaiMonAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loaiMonAn = spnLoaiMonAn.getSelectedItem().toString();
                FilterByType(loaiMonAn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    private void LoadSpinnerData() {
        List<String> regions = new ArrayList<>();
        regions.add("Tất cả");
        regions.add("Bắc");
        regions.add("Trung");
        regions.add("Nam");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, regions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVungMien.setAdapter(spinnerAdapter);

        List<String> types = new ArrayList<>();
        types.add("Tất cả");
        types.add("Món ăn no");
        types.add("Món ăn chơi");
        types.add("Trái cây");
        types.add("Đồ uống");

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiMonAn.setAdapter(typeAdapter);
    }

    private void LoadSQLiteData() {
        try {
            dsMonAn.clear();
            dsMonAn.addAll(dbHelper.getAllMonAn());
            adapterDacSan.notifyDataSetChanged();
            Log.d("DEBUG", "Data loaded from SQLite, size: " + dsMonAn.size());
        } catch (Exception e) {
            Log.e("ERROR", "Error loading data from SQLite: " + e.getMessage());
        }
    }

    private void TimKiemMonAn(String tenMonAn, String vungMien, String loaiMonAn) {
        ArrayList<MonAn> filteredList = new ArrayList<>();
        for (MonAn monAn : dsMonAn) {
            boolean matchesRegion = vungMien.equals("Tất cả") || monAn.getVungMien().equals(vungMien);
            boolean matchesType = loaiMonAn.equals("Tất cả") || monAn.getLoaiMonAn().equals(loaiMonAn);
            boolean matchesTen = tenMonAn.isEmpty() || monAn.getTenMonAn().toLowerCase().contains(tenMonAn.toLowerCase());

            if (matchesRegion && matchesType && matchesTen) {
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

    private void FilterByType(String loaiMonAn) {
        ArrayList<MonAn> filteredList = new ArrayList<>();
        for (MonAn monAn : dsMonAn) {
            if (loaiMonAn.equals("Tất cả") || monAn.getLoaiMonAn().equals(loaiMonAn)) {
                filteredList.add(monAn);
            }
        }

        adapterDacSan.clear();
        adapterDacSan.addAll(filteredList);
        adapterDacSan.notifyDataSetChanged();
    }
}