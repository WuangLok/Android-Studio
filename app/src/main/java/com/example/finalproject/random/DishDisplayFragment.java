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

public class DishDisplayFragment extends Fragment {

    private TextView tvSuggestion;
    private ImageView imgDish;
    private TextView tvDishName;
    private TextView tvDishType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvSuggestion = view.findViewById(R.id.tvSuggestion);
        imgDish = view.findViewById(R.id.imgMonan);
        tvDishName = view.findViewById(R.id.tvtenMonan);
        tvDishType = view.findViewById(R.id.tvloaiMonan);

        tvSuggestion.setText("Hôm nay ăn gì?");

        DBHelper dbHelper = new DBHelper(getContext());
        MonAn randomDish = dbHelper.getRandomMonan();

        if (randomDish != null) {
            tvDishName.setText(randomDish.getTenMonAn());
            tvDishType.setText(randomDish.getLoaiMonAn());

            int drawableId = getResources().getIdentifier(randomDish.getHinhAnh(), "drawable", getContext().getPackageName());
            imgDish.setImageResource(drawableId != 0 ? drawableId : R.drawable.error);
        } else {
            tvDishName.setText("Không có món ăn nào");
            tvDishType.setText("");
            imgDish.setImageResource(R.drawable.error);
        }
    }
}
