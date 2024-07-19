package com.example.finalproject.Yeuthich;

public class YeuThich {
    private int id;
    private String tenMonAn, loaiMonAn, vungMien, hinhAnh, congThuc, lichSu, sangTao;
    private int favorite; // Sử dụng int để lưu trạng thái yêu thích

    public YeuThich() {
    }

    public YeuThich(int id, String tenMonAn, String loaiMonAn, String vungMien, String hinhAnh, String congThuc, String lichSu, String sangTao, boolean favorite) {
        this.id = id;
        this.tenMonAn = tenMonAn;
        this.loaiMonAn = loaiMonAn;
        this.vungMien = vungMien;
        this.hinhAnh = hinhAnh;
        this.congThuc = congThuc;
        this.lichSu = lichSu;
        this.sangTao = sangTao;
        this.favorite = favorite ? 1 : 0; // Chuyển đổi boolean thành int
    }

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
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(String congThuc) {
        this.congThuc = congThuc;
    }

    public String getLichSu() {
        return lichSu;
    }

    public void setLichSu(String lichSu) {
        this.lichSu = lichSu;
    }

    public String getSangTao() {
        return sangTao;
    }

    public void setSangTao(String sangTao) {
        this.sangTao = sangTao;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite == 1;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite ? 1 : 0;
    }
}
