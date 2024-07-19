package com.example.finalproject.Them;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.ViewHolder> {

    private Context context;
    private List<Speciality> specialityList;
    private OnItemClickListener mListener;

    public SpecialityAdapter(Context context, List<Speciality> specialityList) {
        this.context = context;
        this.specialityList = specialityList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_speciality, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Speciality speciality = specialityList.get(position);

        holder.itemName.setText(speciality.getTenMonAn());
        holder.itemType.setText(speciality.getVungMien());

        int imageResource = context.getResources().getIdentifier(speciality.getHinhAnh(), "drawable", context.getPackageName());
        holder.itemImage.setImageResource(imageResource);

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(speciality);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemType;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemType = itemView.findViewById(R.id.item_type);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Speciality speciality);
    }
}
