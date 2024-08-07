/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.sql.Date;

/**
 *
 * @author duyen
 */
public class HoaDon {

    private int maHoaDon;
    private String maNV;
    private String maBanAn;
    private double tongTien;
    private java.sql.Date ngayLap;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, String maNV, String maBanAn, double tongTien, Date ngayLap) {
        this.maHoaDon = maHoaDon;
        this.maNV = maNV;
        this.maBanAn = maBanAn;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaBanAn() {
        return maBanAn;
    }

    public void setMaBanAn(String maBanAn) {
        this.maBanAn = maBanAn;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

}
