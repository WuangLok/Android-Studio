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
        View view = inflater.inflate(R.layout.fragment_random, container, false);

        ImageView imageView = view.findViewById(R.id.imgMonan);
        TextView nameTextView = view.findViewById(R.id.tvtenMonan);
        TextView typeTextView = view.findViewById(R.id.tvloaiMonan);

        MonAn randomMonAn = dbHelper.getRandomMonan();

        if (randomMonAn != null) {
            int resID = getContext().getResources().getIdentifier(randomMonAn.getHinhAnh(), "drawable", getContext().getPackageName());
            if (resID != 0) {
                imageView.setImageResource(resID);
            } else {
                imageView.setImageResource(R.drawable.error); // Placeholder image if drawable resource not found
            }
            nameTextView.setText(randomMonAn.getTenMonAn());
            typeTextView.setText(randomMonAn.getLoaiMonAn());
        } else {
            nameTextView.setText("Không có món ăn nào");
            typeTextView.setText("");
            imageView.setImageResource(R.drawable.error);
        }

        return view;
    }
}
