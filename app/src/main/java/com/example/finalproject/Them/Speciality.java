package com.example.finalproject.Them;

import android.os.Parcel;
import android.os.Parcelable;

public class Speciality implements Parcelable {
    private int id;
    private String tenMonAn;
    private String hinhAnh;
    private String vungMien;
    private String loaiMonAn;
    private String lichsu;
    private String congThuc;
    private String sangtao;
    private boolean favorite;

    public Speciality(int id, String tenMonAn, String hinhAnh, String vungMien, String loaiMonAn, String lichsu, String congThuc, String sangtao, boolean favorite) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.hinhAnh = hinhAnh;
        this.vungMien = vungMien;
        this.loaiMonAn = loaiMonAn;
        this.lichsu = lichsu;
        this.congThuc = congThuc;
        this.sangtao = sangtao;
        this.favorite = favorite;
    }

    protected Speciality(Parcel in) {
        id = in.readInt();
        tenMonAn = in.readString();
        hinhAnh = in.readString();
        vungMien = in.readString();
        loaiMonAn = in.readString();
        lichsu = in.readString();
        congThuc = in.readString();
        sangtao = in.readString();
        favorite = in.readByte() != 0;
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
        dest.writeString(hinhAnh);
        dest.writeString(vungMien);
        dest.writeString(loaiMonAn);
        dest.writeString(lichsu);
        dest.writeString(congThuc);
        dest.writeString(sangtao);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    // Getters for all fields
    public int getId() {
        return id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public String getVungMien() {
        return vungMien;
    }

    public String getLoaiMonAn() {
        return loaiMonAn;
    }

    public String getLichsu() {
        return lichsu;
    }

    public String getCongThuc() {
        return congThuc;
    }

    public String getsangtao() {
        return sangtao;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
