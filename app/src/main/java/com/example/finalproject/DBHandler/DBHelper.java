package com.example.finalproject.DBHandler;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.finalproject.TimKiem.MonAn;
import com.example.finalproject.Yeuthich.YeuThich;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dacsan.db";
    private static final int DATABASE_VERSION = 2; // Tăng version để trigger onUpgrade

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dacsan (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenMonAn TEXT," +
                "loaiMonAn TEXT," +
                "vungMien TEXT," +
                "hinhAnh TEXT," +
                "congThuc TEXT," +
                "lichSu TEXT," +
                "sangTao TEXT," +
                "favorite INTEGER DEFAULT 0," +
                "isUserAdded INTEGER DEFAULT 0" +
                ")");

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE dacsan ADD COLUMN isUserAdded INTEGER DEFAULT 0");
        }
    }

    public void DongGopThongTin(String tenMonAn, String loaiMonAn, String vungMien, String hinhAnh, String congThuc, String lichSu, String sangTao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenMonAn", tenMonAn);
        values.put("loaiMonAn", loaiMonAn);
        values.put("vungMien", vungMien);
        values.put("hinhAnh", hinhAnh);
        values.put("congThuc", congThuc);
        values.put("lichSu", lichSu);
        values.put("sangTao", sangTao);
        values.put("favorite", 0);
        values.put("isUserAdded", 1);  // Đánh dấu món ăn do người dùng thêm vào

        long result = db.insert("dacsan", null, values);
        if (result == -1) {
            Log.e("DBHelper", "Failed to insert new dish");
        } else {
            Log.d("DBHelper", "New dish inserted successfully");
        }
        db.close();
    }

    public boolean updateFavoriteStatus(int id, int favoriteStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite", favoriteStatus);

        int rowsAffected = db.update("dacsan", values, "_id = ?", new String[]{String.valueOf(id)});
        db.close();

        return rowsAffected > 0;
    }

    public void insertSampleData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put("tenMonAn", "Phở");
        values.put("loaiMonAn", "Món ăn no");
        values.put("vungMien", "Bắc");
        values.put("hinhAnh", "pho");
        values.put("congThuc", "Công thức phở");
        values.put("lichSu", "Lịch sử phở");
        values.put("sangTao", "Người sáng tạo phở");
        values.put("favorite", 1);
        values.put("isUserAdded", 0);
        db.insert("dacsan", null, values);

        values.clear();
        values.put("tenMonAn", "Bún Bò Huế");
        values.put("loaiMonAn", "Món ăn no");
        values.put("vungMien", "Trung");
        values.put("hinhAnh", "bunbohue");
        values.put("congThuc", "Công thức bún bò Huế");
        values.put("lichSu", "Lịch sử bún bò Huế");
        values.put("sangTao", "Người sáng tạo bún bò Huế");
        values.put("favorite", 1);
        values.put("isUserAdded", 0);
        db.insert("dacsan", null, values);

        values.clear();
        values.put("tenMonAn", "Hủ Tiếu Nam Vang");
        values.put("loaiMonAn", "Món ăn no");
        values.put("vungMien", "Nam");
        values.put("hinhAnh", "hutieunamvang");
        values.put("congThuc", "Công thức hu tieu nam vang");
        values.put("lichSu", "Lịch sử hu tieu nam vang");
        values.put("sangTao", "Người sáng tạo hu tieu nam vang");
        values.put("favorite", 1);
        values.put("isUserAdded", 0);
        db.insert("dacsan", null, values);
    }

    public List<YeuThich> getAllYeuThich() {
        List<YeuThich> yeuThichList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] columns = {"_id", "tenMonAn", "loaiMonAn", "vungMien", "hinhAnh", "congThuc", "lichSu", "sangTao", "favorite"};
            String selection = "favorite = 1";

            cursor = db.query("dacsan", columns, selection, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    YeuThich yeuThich = new YeuThich();
                    yeuThich.setId(cursor.getInt(0));
                    yeuThich.setTenMonAn(cursor.getString(1));
                    yeuThich.setLoaiMonAn(cursor.getString(2));
                    yeuThich.setVungMien(cursor.getString(3));
                    yeuThich.setHinhAnh(cursor.getString(4));
                    yeuThich.setCongThuc(cursor.getString(5));
                    yeuThich.setLichSu(cursor.getString(6));
                    yeuThich.setSangTao(cursor.getString(7));
                    yeuThich.setFavorite(cursor.getInt(8));

                    yeuThichList.add(yeuThich);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return yeuThichList;
    }

    public List<MonAn> getAllMonAn() {
        List<MonAn> monAnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("dacsan", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                MonAn monAn = new MonAn();
                monAn.setId(cursor.getInt(0));
                monAn.setTenMonAn(cursor.getString(1));
                monAn.setLoaiMonAn(cursor.getString(2));
                monAn.setVungMien(cursor.getString(3));
                monAn.setHinhAnh(cursor.getString(4));
                monAn.setCongThuc(cursor.getString(5));
                monAn.setLichSu(cursor.getString(6));
                monAn.setSangTao(cursor.getString(7));
                monAn.setFarvorite(cursor.getInt(8) == 1);
                monAnList.add(monAn);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return monAnList;
    }

    public void updateFavorite(int monAnId, boolean isFavorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite", isFavorite ? 1 : 0);
        db.update("dacsan", values, "_id = ?", new String[]{String.valueOf(monAnId)});
        db.close();
    }

    @SuppressLint("Range")
    public MonAn getRandomMonan() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        MonAn randomDish = null;

        try {
            String[] columns = {"_id", "tenMonAn", "loaiMonAn", "vungMien", "hinhAnh", "congThuc", "lichSu", "sangTao"};
            cursor = db.query("dacsan", columns, null, null, null, null, "RANDOM()", "1");

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("_id");
                int tenMonAnIndex = cursor.getColumnIndex("tenMonAn");
                int loaiMonAnIndex = cursor.getColumnIndex("loaiMonAn");
                int vungMienIndex = cursor.getColumnIndex("vungMien");
                int hinhAnhIndex = cursor.getColumnIndex("hinhAnh");
                int congThucIndex = cursor.getColumnIndex("congThuc");
                int lichSuIndex = cursor.getColumnIndex("lichSu");
                int sangTaoIndex = cursor.getColumnIndex("sangTao");

                if (idIndex != -1 && tenMonAnIndex != -1 && loaiMonAnIndex != -1 && vungMienIndex != -1 &&
                        hinhAnhIndex != -1 && congThucIndex != -1 && lichSuIndex != -1 && sangTaoIndex != -1) {

                    randomDish = new MonAn();
                    randomDish.setId(cursor.getInt(idIndex));
                    randomDish.setTenMonAn(cursor.getString(tenMonAnIndex));
                    randomDish.setLoaiMonAn(cursor.getString(loaiMonAnIndex));
                    randomDish.setVungMien(cursor.getString(vungMienIndex));
                    randomDish.setHinhAnh(cursor.getString(hinhAnhIndex));
                    randomDish.setCongThuc(cursor.getString(congThucIndex));
                    randomDish.setLichSu(cursor.getString(lichSuIndex));
                    randomDish.setSangTao(cursor.getString(sangTaoIndex));
                }
            }
        } catch (Exception e) {
            Log.e("DBHelper", "Error in getRandomMonan", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        Log.d("DBHelper", "Random MonAn: " + (randomDish != null ? randomDish.getTenMonAn() : "No data"));
        return randomDish;
    }

    public void deleteSpecialityById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("dacsan", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}


