package com.example.finalproject.TimKiem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.R;

import java.util.List;

public class AdapterDacSan extends ArrayAdapter<MonAn> {

    private Context context;
    private List<MonAn> dacsanList;

    public AdapterDacSan(@NonNull Context context, @NonNull List<MonAn> list) {
        super(context, 0 , list);
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

        int resID = context.getResources().getIdentifier(currentDacSan.getHinhAnh(), "drawable", context.getPackageName());
        imageView.setImageResource(resID);

        name.setText(currentDacSan.getTenMonAn());
        type.setText(currentDacSan.getLoaiMonAn());

        Log.d("DEBUG", "Binding data for item: " + currentDacSan.getTenMonAn());

        return listItem;
    }

}

