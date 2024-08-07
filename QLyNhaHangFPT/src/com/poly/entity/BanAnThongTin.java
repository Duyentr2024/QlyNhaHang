/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

/**
 *
 * @author votha
 */
public class BanAnThongTin {
    private String tenBan;
    private String tenMonAn;
    private int soLuong;
    private double giaTien;

    public BanAnThongTin(String tenBan, String tenMonAn, int soLuong, double giaTien) {
        this.tenBan = tenBan;
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    // Getters and setters
    public String getTenBan() { return tenBan; }
    public void setTenBan(String tenBan) { this.tenBan = tenBan; }

    public String getTenMonAn() { return tenMonAn; }
    public void setTenMonAn(String tenMonAn) { this.tenMonAn = tenMonAn; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getGiaTien() { return giaTien; }
    public void setGiaTien(double giaTien) { this.giaTien = giaTien; }
}
