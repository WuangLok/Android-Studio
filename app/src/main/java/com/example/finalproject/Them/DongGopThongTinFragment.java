package com.example.finalproject.Them;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;
import com.example.finalproject.TimKiem.MonAn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DongGopThongTinFragment extends Fragment {

    private Button btnSubmit;
    private Button btnXemDanhSach;
    private Button btnXoa;
    private RecyclerView recyclerView;
    private List<Speciality> specialityList;
    private SpecialityAdapter adapter;
    private DBHelper dbHelper;
    private ImageView ivSelectedImage;
    private TextView tvSelectedImage;
    private String selectedImagePath;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public DongGopThongTinFragment() {
        // Required empty public constructor
    }

    public static DongGopThongTinFragment newInstance(String param1, String param2) {
        DongGopThongTinFragment fragment = new DongGopThongTinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dong_gop_thong_tin, container, false);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnXemDanhSach = view.findViewById(R.id.btnXem);
        btnXoa = view.findViewById(R.id.btnXoa);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        specialityList = new ArrayList<>();
        adapter = new SpecialityAdapter(getContext(), specialityList);
        recyclerView.setAdapter(adapter);

        dbHelper = new DBHelper(getContext());

        loadSpecialitiesFromDatabase();

        btnSubmit.setOnClickListener(v -> showCustomDialog());

        btnXemDanhSach.setOnClickListener(v -> {
            if (!specialityList.isEmpty()) {
                StringBuilder details = new StringBuilder();
                for (Speciality speciality : specialityList) {
                    details.append("Tên: ").append(speciality.getTenMonAn()).append("\n")
                            .append("Hình ảnh: ").append(speciality.getHinhAnh()).append("\n")
                            .append("Vùng miền: ").append(speciality.getVungMien()).append("\n")
                            .append("Loại môn ăn: ").append(speciality.getLoaiMonAn()).append("\n")
                            .append("Lịch sử: ").append(speciality.getLichsu()).append("\n")
                            .append("Công thức: ").append(speciality.getCongThuc()).append("\n")
                            .append("Sáng tạo: ").append(speciality.getsangtao()).append("\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Danh sách món ăn");
                builder.setMessage(details.toString());
                builder.setPositiveButton("Đóng", (dialog, which) -> dialog.dismiss());
                builder.show();
            } else {
                Toast.makeText(getContext(), "Danh sách trống", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoa.setOnClickListener(v -> showDeleteDialog());

        return view;
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_delete_speciality, null);
        builder.setView(dialogView);

        RecyclerView deleteRecyclerView = dialogView.findViewById(R.id.recyclerViewDelete);
        deleteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SpecialityAdapter deleteAdapter = new SpecialityAdapter(getContext(), new ArrayList<>(specialityList));
        deleteAdapter.setDeleteMode(true);
        deleteRecyclerView.setAdapter(deleteAdapter);

        builder.setTitle("Chọn các món ăn để xóa")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    List<Speciality> selectedSpecialities = deleteAdapter.getSelectedSpecialities();
                    if (selectedSpecialities.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng chọn ít nhất một món ăn để xóa", Toast.LENGTH_SHORT).show();
                    } else {
                        for (Speciality speciality : selectedSpecialities) {
                            dbHelper.deleteSpecialityById(speciality.getId());
                        }
                        deleteAdapter.removeSelectedSpecialities();
                        loadSpecialitiesFromDatabase();
                        Toast.makeText(getContext(), "Đã xóa các món ăn được chọn thành công!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> deleteAdapter.setDeleteMode(false))
                .show();
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_speciality, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        Spinner spnRegion = dialogView.findViewById(R.id.spnRegion);
        Spinner spnLoaiMonAn = dialogView.findViewById(R.id.spnLoaiMonAn);
        EditText etLichsu = dialogView.findViewById(R.id.etLichsu);
        EditText etSangtao = dialogView.findViewById(R.id.etSangtao);
        EditText etCongThuc = dialogView.findViewById(R.id.etCongThuc);
        Button btnSubmit1 = dialogView.findViewById(R.id.btnSubmit1);
        Button btnSelectImage = dialogView.findViewById(R.id.btnSelectImage);
        ivSelectedImage = dialogView.findViewById(R.id.ivSelectedImage);
        tvSelectedImage = dialogView.findViewById(R.id.tvSelectedImage);

        // Set up the region spinner
        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.region_array, android.R.layout.simple_spinner_item);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRegion.setAdapter(regionAdapter);

        // Set up the loai mon an spinner
        ArrayAdapter<CharSequence> loaiMonAnAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.loai_mon_an_array, android.R.layout.simple_spinner_item);
        loaiMonAnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiMonAn.setAdapter(loaiMonAnAdapter);

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        btnSelectImage.setOnClickListener(v -> showImagePickerDialog());

        btnSubmit1.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String image = selectedImagePath != null ? selectedImagePath : "";
            String region = spnRegion.getSelectedItem().toString();
            String loaiMonAn = spnLoaiMonAn.getSelectedItem().toString();
            String lichsu = etLichsu.getText().toString();
            String sangtao = etSangtao.getText().toString();
            String congThuc = etCongThuc.getText().toString();

            if (name.isEmpty() || image.isEmpty() || region.isEmpty() || loaiMonAn.isEmpty() || lichsu.isEmpty() || sangtao.isEmpty() || congThuc.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.DongGopThongTin(name, image, region, loaiMonAn, lichsu, congThuc, sangtao);

                Speciality newSpeciality = new Speciality(
                        specialityList.size() + 1,
                        name,
                        image,
                        region,
                        loaiMonAn,
                        congThuc,
                        lichsu,
                        sangtao,
                        false

                );
                specialityList.add(newSpeciality);
                adapter.notifyItemInserted(specialityList.size() - 1);
                Toast.makeText(getContext(), "Đã đóng góp thành công!", Toast.LENGTH_SHORT).show();

                if (specialityList.size() > 0) {
                    btnXemDanhSach.setVisibility(View.VISIBLE);
                }

                dialog.dismiss();
            }
        });
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search_image, null);
        builder.setView(dialogView);

        EditText etSearchImage = dialogView.findViewById(R.id.etSearchImage);
        ListView lvImages = dialogView.findViewById(R.id.lvImages);

        String[] drawableNames = getDrawableNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, drawableNames);
        lvImages.setAdapter(adapter);

        etSearchImage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        lvImages.setOnItemClickListener((parent, view, position, id) -> {
            String selectedImageName = adapter.getItem(position);
            selectedImagePath = selectedImageName;
            int resID = getResources().getIdentifier(selectedImageName, "drawable", getContext().getPackageName());
            ivSelectedImage.setImageResource(resID);
            ivSelectedImage.setVisibility(View.VISIBLE);
            tvSelectedImage.setText(selectedImageName);
            tvSelectedImage.setVisibility(View.VISIBLE);
            builder.create().dismiss();
        });

        builder.setTitle("Chọn ảnh từ drawable")
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private String[] getDrawableNames() {
        Field[] fields = R.drawable.class.getDeclaredFields();
        List<String> drawableNames = new ArrayList<>();
        for (Field field : fields) {
            if (!field.getName().startsWith("notify") && !field.getName().startsWith("abc")) { // Exclude common unwanted drawables
                drawableNames.add(field.getName());
            }
        }
        return drawableNames.toArray(new String[0]);
    }

    private void loadSpecialitiesFromDatabase() {
        specialityList.clear();
        List<MonAn> monAnList = dbHelper.getAllMonAn();
        for (MonAn monAn : monAnList) {
            Speciality speciality = new Speciality(
                    monAn.getId(),
                    monAn.getTenMonAn(),
                    monAn.getHinhAnh(),
                    monAn.getVungMien(),
                    monAn.getLoaiMonAn(),
                    monAn.getLichSu(),
                    monAn.getCongThuc(),
                    monAn.getSangTao(),
                    monAn.isFarvorite()

            );
            specialityList.add(speciality);
        }
        adapter.notifyDataSetChanged();
    }
}
