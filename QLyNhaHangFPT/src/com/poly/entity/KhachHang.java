package com.poly.entity;

public class KhachHang {
    private String maKH;
    private String hoTenKH;
    private String sdtKH;

    public KhachHang() {
    }

    public KhachHang(String maKH, String hoTenKH, String sdtKH) {
        this.maKH = maKH;
        this.hoTenKH = hoTenKH;
        this.sdtKH = sdtKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTenKH() {
        return hoTenKH;
    }

    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH='" + maKH + '\'' +
                ", hoTenKH='" + hoTenKH + '\'' +
                ", sdtKH='" + sdtKH + '\'' +
                '}';
    }
}
