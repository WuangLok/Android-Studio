package com.example.finalproject.TimKiem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

import java.util.List;

public class AdapterDacSan extends ArrayAdapter<MonAn> {

    private Context context;
    private List<MonAn> dacsanList;

    public AdapterDacSan(@NonNull Context context, @NonNull List<MonAn> list) {
        super(context, 0, list);
        this.context = context;
        this.dacsanList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.customlistview_timkiem, parent, false);
        }

        MonAn currentDacSan = dacsanList.get(position);

        ImageView imageView = listItem.findViewById(R.id.item_image);
        TextView name = listItem.findViewById(R.id.item_name);
        TextView type = listItem.findViewById(R.id.item_type);
        ImageButton btnHeart = listItem.findViewById(R.id.btnHeart);

        int resID = context.getResources().getIdentifier(currentDacSan.getHinhAnh(), "drawable", context.getPackageName());
        imageView.setImageResource(resID);

        name.setText(currentDacSan.getTenMonAn());
        type.setText(currentDacSan.getLoaiMonAn());

        btnHeart.setImageResource(currentDacSan.isFarvorite() ? R.drawable.redheart : R.drawable.emptyheart);

        btnHeart.setOnClickListener(v -> {
            boolean newFavoriteStatus = !currentDacSan.isFarvorite();
            currentDacSan.setFarvorite(newFavoriteStatus);

            DBHelper dbHelper = new DBHelper(context);
            dbHelper.updateFavorite(currentDacSan.getId(), newFavoriteStatus);

            btnHeart.setImageResource(newFavoriteStatus ? R.drawable.redheart : R.drawable.emptyheart);
        });

        Log.d("DEBUG", "Binding data for item: " + currentDacSan.getTenMonAn());

        // Đảm bảo rằng nút trái tim không chặn sự kiện nhấp vào phần còn lại của mục
        btnHeart.setFocusable(false);
        btnHeart.setFocusableInTouchMode(false);
        btnHeart.setClickable(true);

        return listItem;
    }
}
