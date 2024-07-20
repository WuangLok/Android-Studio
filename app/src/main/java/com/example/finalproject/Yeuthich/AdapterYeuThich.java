package com.example.finalproject.Yeuthich;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

import java.util.ArrayList;

public class AdapterYeuThich extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<YeuThich> yeuThichList;

    public AdapterYeuThich(Context context, int layout, ArrayList<YeuThich> yeuThichList) {
        this.context = context;
        this.layout = layout;
        this.yeuThichList = yeuThichList;
    }

    @Override
    public int getCount() {
        return yeuThichList.size();
    }

    @Override
    public Object getItem(int position) {
        return yeuThichList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Return position as ID
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgItem = convertView.findViewById(R.id.item_image);
            holder.txtName = convertView.findViewById(R.id.item_name);
            holder.txtType = convertView.findViewById(R.id.item_type);
            holder.btnHeart = convertView.findViewById(R.id.btnHeart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        YeuThich yeuThich = yeuThichList.get(position);

        // Assuming getHinhAnh() returns the name of the image resource
        int resID = context.getResources().getIdentifier(yeuThich.getHinhAnh(), "drawable", context.getPackageName());
        holder.imgItem.setImageResource(resID);

        holder.txtName.setText(yeuThich.getTenMonAn());
        holder.txtType.setText(yeuThich.getLoaiMonAn());
    
        if (yeuThich.getFavorite() == 1) {
            holder.btnHeart.setImageResource(R.drawable.redheart);
        } else {
            holder.btnHeart.setImageResource(R.drawable.emptyheart);
        }

        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Toggle favorite status
                yeuThich.setFavorite(yeuThich.getFavorite() == 1 ? 0 : 1);

                // Update the database
                ContentValues values = new ContentValues();
                values.put("favorite", yeuThich.getFavorite());

                // Perform the update using the correct query format
                db.update("dacsan", values, "_id = ?", new String[]{String.valueOf(yeuThich.getId())});

                // Notify the adapter that the dataset has changed
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgItem;
        TextView txtName;
        TextView txtType;
        ImageButton btnHeart;
    }
}
