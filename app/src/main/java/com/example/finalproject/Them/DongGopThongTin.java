package com.example.finalproject.Them;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;
import com.example.finalproject.TimKiem.MonAn;

import java.util.ArrayList;
import java.util.List;

public class DongGopThongTin extends AppCompatActivity {

    private Button btnSubmit;
    private Button btnXemDanhSach;
    private Button btnXoa;
    private RecyclerView recyclerView;
    private List<Speciality> specialityList;
    private SpecialityAdapter adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_gop_thong_tin);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnXemDanhSach = findViewById(R.id.btnXem);
        btnXoa = findViewById(R.id.btnXoa);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        specialityList = new ArrayList<>();
        adapter = new SpecialityAdapter(this, specialityList);
        recyclerView.setAdapter(adapter);

        dbHelper = new DBHelper(this);

        loadSpecialitiesFromDatabase();

        btnSubmit.setOnClickListener(view -> showCustomDialog());

        btnXemDanhSach.setOnClickListener(view -> {
            if (!specialityList.isEmpty()) {
                StringBuilder details = new StringBuilder();
                for (Speciality speciality : specialityList) {
                    details.append("Tên: ").append(speciality.getTenMonAn()).append("\n")
                            .append("Hình ảnh: ").append(speciality.getHinhAnh()).append("\n")
                            .append("Vùng miền: ").append(speciality.getVungMien()).append("\n")
                            .append("Lịch sử: ").append(speciality.getlichsu()).append("\n")
                            .append("Sáng tạo: ").append(speciality.getsangtao()).append("\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DongGopThongTin.this);
                builder.setTitle("Danh sách món ăn");
                builder.setMessage(details.toString());
                builder.setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss());
                builder.show();
            } else {
                Toast.makeText(DongGopThongTin.this, "Danh sách trống", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoa.setOnClickListener(view -> showDeleteDialog());
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_delete_speciality, null);
        builder.setView(dialogView);

        RecyclerView deleteRecyclerView = dialogView.findViewById(R.id.recyclerViewDelete);
        deleteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SpecialityAdapter deleteAdapter = new SpecialityAdapter(this, new ArrayList<>(specialityList));
        deleteAdapter.setDeleteMode(true);
        deleteRecyclerView.setAdapter(deleteAdapter);

        builder.setTitle("Chọn các món ăn để xóa")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    List<Speciality> selectedSpecialities = deleteAdapter.getSelectedSpecialities();
                    if (selectedSpecialities.isEmpty()) {
                        Toast.makeText(DongGopThongTin.this, "Vui lòng chọn ít nhất một món ăn để xóa", Toast.LENGTH_SHORT).show();
                    } else {
                        for (Speciality speciality : selectedSpecialities) {
                            dbHelper.deleteSpecialityById(speciality.getId());
                        }
                        deleteAdapter.removeSelectedSpecialities();
                        loadSpecialitiesFromDatabase();
                        Toast.makeText(DongGopThongTin.this, "Đã xóa các món ăn được chọn thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> deleteAdapter.setDeleteMode(false))  // Reset delete mode on cancel
                .show();
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_speciality, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etImage = dialogView.findViewById(R.id.etImage);
        EditText etRegion = dialogView.findViewById(R.id.etRegion);
        EditText etLichsu = dialogView.findViewById(R.id.etLichsu);
        EditText etSangtao = dialogView.findViewById(R.id.etSangtao);
        Button btnSubmit1 = dialogView.findViewById(R.id.btnSubmit1);

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        btnSubmit1.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String image = etImage.getText().toString();
            String region = etRegion.getText().toString();
            String lichsu = etLichsu.getText().toString();
            String sangtao = etSangtao.getText().toString();

            if (name.isEmpty() || image.isEmpty() || region.isEmpty() || lichsu.isEmpty() || sangtao.isEmpty()) {
                Toast.makeText(DongGopThongTin.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.DongGopThongTin(name, "Món chính", region, image, "Công thức đang cập nhật", lichsu, sangtao);

                Speciality newSpeciality = new Speciality(
                        specialityList.size() + 1,
                        name,
                        region,
                        image,
                        lichsu,
                        sangtao,
                        false
                );
                specialityList.add(newSpeciality);
                adapter.notifyItemInserted(specialityList.size() - 1);
                Toast.makeText(DongGopThongTin.this, "Đã đóng góp thành công!", Toast.LENGTH_SHORT).show();

                if (specialityList.size() > 0) {
                    btnXemDanhSach.setVisibility(View.VISIBLE);
                }

                dialog.dismiss();
            }
        });
    }

    private void loadSpecialitiesFromDatabase() {
        specialityList.clear();
        List<MonAn> monAnList = dbHelper.getAllMonAn();
        for (MonAn monAn : monAnList) {
            Speciality speciality = new Speciality(
                    monAn.getId(),
                    monAn.getTenMonAn(),
                    monAn.getVungMien(),
                    monAn.getHinhAnh(),
                    monAn.getLichSu(),
                    monAn.getSangTao(),
                    monAn.isFarvorite()
            );
            specialityList.add(speciality);
        }
        adapter.notifyDataSetChanged();
    }
}
