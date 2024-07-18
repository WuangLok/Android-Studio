package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.TimKiem.MonAn;

import java.util.List;

public class AdapterDacSanRandom extends ArrayAdapter<MonAn> {

    private Context context;
    private List<MonAn> dacsanList;

    public AdapterDacSanRandom(@NonNull Context context, @NonNull List<MonAn> list) {
        super(context, 0, list);
        this.context = context;
        this.dacsanList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.customlv_hiendacsanngaunhien, parent, false);
        }

        MonAn currentDacSan = dacsanList.get(position);

        ImageView imageView = listItem.findViewById(R.id.item_image);
        TextView name = listItem.findViewById(R.id.item_name);
        TextView type = listItem.findViewById(R.id.item_type);

        int resID = context.getResources().getIdentifier(currentDacSan.getHinhAnh(), "drawable", context.getPackageName());
        imageView.setImageResource(resID);

        name.setText(currentDacSan.getTenMonAn());
        type.setText(currentDacSan.getLoaiMonAn());

        return listItem;
    }
}
