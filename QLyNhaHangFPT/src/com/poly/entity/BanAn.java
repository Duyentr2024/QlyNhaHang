package com.poly.entity;

public class BanAn {
    private String maBanAn;
    private String tenBan;
    private String maKhuVuc;
    boolean TrangThai;

    public BanAn() {
    }

    public BanAn(String maBanAn, String tenBan, String maKhuVuc, boolean TrangThai) {
        this.maBanAn = maBanAn;
        this.tenBan = tenBan;
        this.maKhuVuc = maKhuVuc;
        this.TrangThai = TrangThai;
    }

    public String getMaBanAn() {
        return maBanAn;
    }

    public void setMaBanAn(String maBanAn) {
        this.maBanAn = maBanAn;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    
    
   
}
