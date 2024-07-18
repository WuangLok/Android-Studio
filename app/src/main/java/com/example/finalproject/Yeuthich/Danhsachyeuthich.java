package com.example.finalproject.Yeuthich;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;
import java.util.ArrayList;

public class Danhsachyeuthich extends AppCompatActivity {

    private static final String TAG = "Danhsachyeuthich";

    ListView lvYeuThich;
    AdapterYeuThich customAdapter;
    ArrayList<YeuThich> yeuThichList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachyeuthich);

        lvYeuThich = findViewById(R.id.lvDanhSachYeuThich);
        yeuThichList = new ArrayList<>();
        dbHelper = new DBHelper(this);

        // Load favorite dishes from the database
        yeuThichList.addAll(dbHelper.getAllYeuThich());

        // Log the size of yeuThichList to check if any items were loaded
        Log.d(TAG, "Number of favorites loaded: " + yeuThichList.size());

        // Setup adapter
        customAdapter = new AdapterYeuThich(this, R.layout.customlistview_yeuthich, yeuThichList);
        lvYeuThich.setAdapter(customAdapter);
    }
}
