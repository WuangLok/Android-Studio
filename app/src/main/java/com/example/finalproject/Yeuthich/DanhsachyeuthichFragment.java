package com.example.finalproject.Yeuthich;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.DBHandler.DBHelper;
import com.example.finalproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhsachyeuthichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhsachyeuthichFragment extends Fragment {

    private static final String TAG = "DanhsachyeuthichFragment";

    ListView lvYeuThich;
    AdapterYeuThich customAdapter;
    ArrayList<YeuThich> yeuThichList;
    DBHelper dbHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhsachyeuthichFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhsachyeuthichFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhsachyeuthichFragment newInstance(String param1, String param2) {
        DanhsachyeuthichFragment fragment = new DanhsachyeuthichFragment();
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
        View view = inflater.inflate(R.layout.fragment_danhsachyeuthich, container, false);

        lvYeuThich = view.findViewById(R.id.lvDanhSachYeuThich);
        yeuThichList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());

        // Load favorite dishes from the database
        yeuThichList.addAll(dbHelper.getAllYeuThich());

        // Log the size of yeuThichList to check if any items were loaded
        Log.d(TAG, "Number of favorites loaded: " + yeuThichList.size());

        // Setup adapter
        customAdapter = new AdapterYeuThich(getContext(), R.layout.customlistview_yeuthich, yeuThichList);
        lvYeuThich.setAdapter(customAdapter);

        return view;
    }
}