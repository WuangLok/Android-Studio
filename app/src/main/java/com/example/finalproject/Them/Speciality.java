package com.example.finalproject.Them;

import android.os.Parcel;
import android.os.Parcelable;

public class Speciality implements Parcelable {
    private int id;
    private String tenMonAn;
    private String vungMien;
    private String hinhAnh;
    private String lichsu;
    private String sangtao;
    private boolean favorite;
    private String congThuc;

    public Speciality(int id, String tenMonAn, String vungMien, String hinhAnh, String lichsu, String sangtao, boolean favorite, String congThuc) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.vungMien = vungMien;
        this.hinhAnh = hinhAnh;
        this.lichsu = lichsu;
        this.sangtao = sangtao;
        this.favorite = favorite;
        this.congThuc = congThuc;
    }

    protected Speciality(Parcel in) {
        id = in.readInt();
        tenMonAn = in.readString();
        vungMien = in.readString();
        hinhAnh = in.readString();
        lichsu = in.readString();
        sangtao = in.readString();
        favorite = in.readByte() != 0;
        congThuc = in.readString();
    }

    public static final Creator<Speciality> CREATOR = new Creator<Speciality>() {
        @Override
        public Speciality createFromParcel(Parcel in) {
            return new Speciality(in);
        }

        @Override
        public Speciality[] newArray(int size) {
            return new Speciality[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tenMonAn);
        dest.writeString(vungMien);
        dest.writeString(hinhAnh);
        dest.writeString(lichsu);
        dest.writeString(sangtao);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeString(congThuc);
    }

    // Getters for all fields
    public int getId() {
        return id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public String getVungMien() {
        return vungMien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public String getLichsu() {
        return lichsu;
    }

    public String getsangtao() {
        return sangtao;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getCongThuc() {
        return congThuc;
    }
}
