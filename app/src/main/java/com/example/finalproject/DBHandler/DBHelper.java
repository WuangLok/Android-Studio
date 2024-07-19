package com.example.finalproject.DBHandler;

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
    private static final int DATABASE_VERSION = 1;


    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the 'dacsan' table
        db.execSQL("CREATE TABLE dacsan (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenMonAn TEXT," +
                "loaiMonAn TEXT," +
                "vungMien TEXT," +
                "hinhAnh TEXT," +
                "congThuc TEXT," +
                "lichSu TEXT," +
                "sangTao TEXT," +
                "favorite INTEGER DEFAULT 0" +
                ")");

        // Insert sample data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate the table if upgrading the database
        db.execSQL("DROP TABLE IF EXISTS dacsan");
        onCreate(db);
    }
    public boolean updateFavoriteStatus(int id, int favoriteStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite", favoriteStatus);

        int rowsAffected = db.update("dacsan", values, "_id = ?", new String[]{String.valueOf(id)});
        db.close();

        return rowsAffected > 0;
    }

    // Method to insert sample data into the database
    public void insertSampleData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Insert Phở
        values.put("tenMonAn", "Phở");
        values.put("loaiMonAn", "Món chính");
        values.put("vungMien", "Bắc");
        values.put("hinhAnh", "pho");
        values.put("congThuc", "Công thức phở");
        values.put("lichSu", "Lịch sử phở");
        values.put("sangTao", "Người sáng tạo phở");
        values.put("favorite", 1);
        db.insert("dacsan", null, values);

        // Insert Bún bò Huế
        values.clear();
        values.put("tenMonAn", "Bún bò Huế");
        values.put("loaiMonAn", "Món chính");
        values.put("vungMien", "Trung");
        values.put("hinhAnh", "bunbohue");
        values.put("congThuc", "Công thức bún bò Huế");
        values.put("lichSu", "Lịch sử bún bò Huế");
        values.put("sangTao", "Người sáng tạo bún bò Huế");
        values.put("favorite", 1);
        db.insert("dacsan", null, values);

        // Insert Hu Tieu Nam Vang
        values.clear();
        values.put("tenMonAn", "Hu Tieu Nam Vang");
        values.put("loaiMonAn", "Món chính");
        values.put("vungMien", "Nam");
        values.put("hinhAnh", "hutieunamvang");
        values.put("congThuc", "Công thức hu tieu nam vang");
        values.put("lichSu", "Lịch sử hu tieu nam vang");
        values.put("sangTao", "Người sáng tạo hu tieu nam vang");
        values.put("favorite", 1);
        db.insert("dacsan", null, values);

        // Add more sample data as needed
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
                    yeuThich.setId(cursor.getInt(0));  // Vị trí cột _id
                    yeuThich.setTenMonAn(cursor.getString(1));  // Vị trí cột tenMonAn
                    yeuThich.setLoaiMonAn(cursor.getString(2));  // Vị trí cột loaiMonAn
                    yeuThich.setVungMien(cursor.getString(3));  // Vị trí cột vungMien
                    yeuThich.setHinhAnh(cursor.getString(4));  // Vị trí cột hinhAnh
                    yeuThich.setCongThuc(cursor.getString(5));  // Vị trí cột congThuc
                    yeuThich.setLichSu(cursor.getString(6));  // Vị trí cột lichSu
                    yeuThich.setSangTao(cursor.getString(7));  // Vị trí cột sangTao
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
                monAn.setId(cursor.getInt(0));  // Vị trí cột _id
                monAn.setTenMonAn(cursor.getString(1));  // Vị trí cột tenMonAn
                monAn.setLoaiMonAn(cursor.getString(2));  // Vị trí cột loaiMonAn
                monAn.setVungMien(cursor.getString(3));  // Vị trí cột vungMien
                monAn.setHinhAnh(cursor.getString(4));  // Vị trí cột hinhAnh
                monAn.setCongThuc(cursor.getString(5));  // Vị trí cột congThuc
                monAn.setLichSu(cursor.getString(6));  // Vị trí cột lichSu
                monAn.setSangTao(cursor.getString(7));  // Vị trí cột sangTao
                monAn.setFarvorite(cursor.getInt(8) == 1);  // Vị trí cột favorite
                monAnList.add(monAn);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return monAnList;
    }
    public void updateFavorite(int monAnId, boolean isFavorite) {
        SQLiteDatabase db = this.getReadableDatabase();

            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("favorite", isFavorite ? 1 : 0);  // Assuming `favorite` column is of type INTEGER
            db.update("dacsan", values, "_id = ?", new String[]{String.valueOf(monAnId)});
            db.close();
        }



    public MonAn getRandomMonan() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        MonAn randomDish = null;

        try {
            String[] columns = {"_id", "tenMonAn", "loaiMonAn", "vungMien", "hinhAnh", "congThuc", "lichSu", "sangTao", "favorite"};
            cursor = db.query("dacsan", columns, "favorite = 1", null, null, null, "RANDOM()", "1");

            if (cursor != null && cursor.moveToFirst()) {
                randomDish = new MonAn();
                randomDish.setId(cursor.getInt(0));
                randomDish.setTenMonAn(cursor.getString(1));
                randomDish.setLoaiMonAn(cursor.getString(2));
                randomDish.setVungMien(cursor.getString(3));
                randomDish.setHinhAnh(cursor.getString(4));
                randomDish.setCongThuc(cursor.getString(5));
                randomDish.setLichSu(cursor.getString(6));
                randomDish.setSangTao(cursor.getString(7));
                randomDish.setFarvorite(cursor.getInt(8) == 1);
            } else {
                Log.d("DBHelper", "No random dish found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        Log.d("DBHelper", "Random MonAn: " + (randomDish != null ? randomDish.getTenMonAn() : "No data"));
        return randomDish;
    }







