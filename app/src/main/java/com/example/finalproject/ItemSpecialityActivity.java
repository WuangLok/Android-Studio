package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemSpecialityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpecialityAdapter adapter;
    private List<Speciality> specialityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_speciality);

        specialityList = getIntent().getParcelableArrayListExtra("specialityList");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SpecialityAdapter(this, specialityList);
        recyclerView.setAdapter(adapter);
    }
}
