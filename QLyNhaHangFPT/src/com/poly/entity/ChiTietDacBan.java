/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

import java.util.Objects;

public class ChiTietDacBan {
    private String maKH; // Optional if you need to track it
    private String maBanAn;
    private String ngayDatBan;
    private double tienCoc;
    private String moTa;

    // Default constructor
    public ChiTietDacBan() {
    }

    // Parameterized constructor
    public ChiTietDacBan(String maBanAn, String ngayDatBan, double tienCoc, String moTa) {
        this.maBanAn = maBanAn;
        this.ngayDatBan = ngayDatBan;
        this.tienCoc = tienCoc;
        this.moTa = moTa;
    }

    // Getters and Setters
    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaBanAn() {
        return maBanAn;
    }

    public void setMaBanAn(String maBanAn) {
        this.maBanAn = maBanAn;
    }

    public String getNgayDatBan() {
        return ngayDatBan;
    }

    public void setNgayDatBan(String ngayDatBan) {
        this.ngayDatBan = ngayDatBan;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietDacBan that = (ChiTietDacBan) o;
        return Double.compare(that.tienCoc, tienCoc) == 0 &&
               Objects.equals(maKH, that.maKH) &&
               Objects.equals(maBanAn, that.maBanAn) &&
               Objects.equals(ngayDatBan, that.ngayDatBan) &&
               Objects.equals(moTa, that.moTa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maKH, maBanAn, ngayDatBan, tienCoc, moTa);
    }

    // Override toString
    @Override
    public String toString() {
        return "ChiTietDacBan{" +
               "maKH='" + maKH + '\'' +
               ", maBanAn='" + maBanAn + '\'' +
               ", ngayDatBan='" + ngayDatBan + '\'' +
               ", tienCoc=" + tienCoc +
               ", moTa='" + moTa + '\'' +
               '}';
    }
}