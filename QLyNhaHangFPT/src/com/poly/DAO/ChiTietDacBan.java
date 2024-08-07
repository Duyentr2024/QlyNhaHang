/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.KhachHang;

/**
 *
 * @author votha
 */
public class ChiTietDacBan {

    private String maKH;
    private String maBanAn;
    private String ngayDatBan;
    private String moTa;
    private double tienCoc;

    public ChiTietDacBan() {
    }

    public ChiTietDacBan(String maKH, String maBanAn, String ngayDatBan, String moTa, double tienCoc) {
        this.maKH = maKH;
        this.maBanAn = maBanAn;
        this.ngayDatBan = ngayDatBan;
        this.moTa = moTa;
        this.tienCoc = tienCoc;
    }

    // Getters and Setters for all fields

    public void setKhachHang(KhachHang khachHang) {
        if (khachHang != null) {
            this.maKH = khachHang.getMaKH(); // Giả sử KhachHang có phương thức getMaKH()
        }
    }

//    public void setBanAn(BanAn banAn) {
//        if (banAn != null) {
//            this.maBanAn = banAn.getMaBanAn(); // Giả sử BanAn có phương thức getMaBanAn()
//        }
//    }
}