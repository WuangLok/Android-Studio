package com.example.finalproject;

public class MonAn {
 private int id;
 private String tenMonAn , loaiMonAn, vungMien,HinhAnh,CongThuc,LichSu,SangTao;
 private boolean farvorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getLoaiMonAn() {
        return loaiMonAn;
    }

    public void setLoaiMonAn(String loaiMonAn) {
        this.loaiMonAn = loaiMonAn;
    }

    public String getVungMien() {
        return vungMien;
    }

    public void setVungMien(String vungMien) {
        this.vungMien = vungMien;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public String getCongThuc() {
        return CongThuc;
    }

    public void setCongThuc(String congThuc) {
        CongThuc = congThuc;
    }

    public String getLichSu() {
        return LichSu;
    }

    public void setLichSu(String lichSu) {
        LichSu = lichSu;
    }

    public String getSangTao() {
        return SangTao;
    }

    public void setSangTao(String sangTao) {
        SangTao = sangTao;
    }

    public boolean isFarvorite() {
        return farvorite;
    }

    public void setFarvorite(boolean farvorite) {
        this.farvorite = farvorite;
    }

    public MonAn(int id, String tenMonAn, String loaiMonAn, String vungMien, String hinhAnh, String congThuc, String lichSu, String sangTao, boolean farvorite) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.loaiMonAn = loaiMonAn;
        this.vungMien = vungMien;
        HinhAnh = hinhAnh;
        CongThuc = congThuc;
        LichSu = lichSu;
        SangTao = sangTao;
        this.farvorite = farvorite;
    }
}
