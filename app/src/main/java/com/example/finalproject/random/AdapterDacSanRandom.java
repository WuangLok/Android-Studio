package com.example.finalproject.random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalproject.R;
import com.example.finalproject.TimKiem.MonAn;

public class AdapterDacSanRandom extends RecyclerView.Adapter<AdapterDacSanRandom.ViewHolder> {
    private Context context;
    private MonAn randomDish;

    public AdapterDacSanRandom(Context context, MonAn randomDish) {
        this.context = context;
        this.randomDish = randomDish;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_random, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (randomDish != null) {
            holder.tenMonAn.setText(randomDish.getTenMonAn());
            holder.loaiMonAn.setText(randomDish.getLoaiMonAn());
            holder.vungMien.setText(randomDish.getVungMien());
            holder.congThuc.setText(randomDish.getCongThuc());
            holder.lichSu.setText(randomDish.getLichSu());
            holder.sangTao.setText(randomDish.getSangTao());
        }
    }

    @Override
    public int getItemCount() {
        return randomDish != null ? 1 : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenMonAn, loaiMonAn, vungMien, congThuc, lichSu, sangTao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenMonAn = itemView.findViewById(R.id.tvtenMonan);
            loaiMonAn = itemView.findViewById(R.id.tvloaiMonan);
        }
    }
}
