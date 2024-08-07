/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

/**
 *
 * @author votha
 */
public class MonAn {
    private int maMonAn;
    private String tenMonAn;
    private Integer maLoaiMon;
    private String dvt;
    private double donGia;
    private String moTa;
    private boolean trangThai;
    private String hinhAnhMonAn;

    public MonAn() {
    }

    public MonAn(int maMonAn, String tenMonAn, Integer maLoaiMon, String dvt, double donGia, String moTa, boolean trangThai, String hinhAnhMonAn) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.maLoaiMon = maLoaiMon;
        this.dvt = dvt;
        this.donGia = donGia;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.hinhAnhMonAn = hinhAnhMonAn;
    }

    public int getMaMonAn() {
        return maMonAn;
    }

    public void setMaMonAn(int maMonAn) {
        this.maMonAn = maMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public Integer getMaLoaiMon() {
        return maLoaiMon;
    }

    public void setMaLoaiMon(Integer maLoaiMon) {
        this.maLoaiMon = maLoaiMon;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnhMonAn() {
        return hinhAnhMonAn;
    }

    public void setHinhAnhMonAn(String hinhAnhMonAn) {
        this.hinhAnhMonAn = hinhAnhMonAn;
    }
    
    
    
}
