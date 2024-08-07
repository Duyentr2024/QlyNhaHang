package com.poly.entity;

public class BanAn {

    private String maBanAn;
    private String tenBan;
    private boolean trangThai;
    private String maKhuVuc;
    private String banAnGoc;

    public BanAn() {
    }

    public BanAn(String maBanAn, String tenBan, boolean trangThai, String maKhuVuc, String banAnGoc) {
        this.maBanAn = maBanAn;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.maKhuVuc = maKhuVuc;
        this.banAnGoc = banAnGoc;
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

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaKhuVuc() {
        return maKhuVuc;
    }

    public void setMaKhuVuc(String maKhuVuc) {
        this.maKhuVuc = maKhuVuc;
    }

    public String getBanAnGoc() {
        return banAnGoc;
    }

    public void setBanAnGoc(String banAnGoc) {
        this.banAnGoc = banAnGoc;
    }

    
    
}
