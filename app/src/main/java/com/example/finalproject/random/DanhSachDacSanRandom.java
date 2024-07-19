package com.example.finalproject.random;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.finalproject.R;

public class DanhSachDacSanRandom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachdacsanrandom);

        // Chỉ khởi tạo Fragment nếu không có trạng thái lưu lại
        if (savedInstanceState == null) {
            RandomFragment randomFragment = RandomFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, randomFragment);
            fragmentTransaction.commit();
        }
    }
}
