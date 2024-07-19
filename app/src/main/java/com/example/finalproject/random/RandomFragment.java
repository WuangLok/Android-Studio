package com.example.finalproject.random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;
import com.example.finalproject.TimKiem.MonAn;

public class RandomFragment extends Fragment {

    private DBHelper dbHelper;

    public RandomFragment() {
        // Required empty public constructor
    }

    public static RandomFragment newInstance() {
        return new RandomFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.imgMonan);
        TextView nameTextView = view.findViewById(R.id.tvtenMonan);
        TextView typeTextView = view.findViewById(R.id.tvloaiMonan);

        MonAn randomMonAn = dbHelper.getRandomMonan();

        if (randomMonAn != null) {
            // Set dish image
            int resID = getContext().getResources().getIdentifier(randomMonAn.getHinhAnh(), "drawable", getContext().getPackageName());
            imageView.setImageResource(resID != 0 ? resID : R.drawable.error);

            // Set dish name and type
            nameTextView.setText(randomMonAn.getTenMonAn());
            typeTextView.setText(randomMonAn.getLoaiMonAn());
        } else {
            // Handle case where no dish is found
            nameTextView.setText("Không có món ăn nào");
            typeTextView.setText("");
            imageView.setImageResource(R.drawable.error);
        }
    }
}

