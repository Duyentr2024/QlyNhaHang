/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

/**
 *
 * @author votha
 */
public class KhuVuc {
    private String MaKhuVuc;
    private String TenKhuVuc;
    private String MoTa;

    public KhuVuc() {
    }

    public KhuVuc(String MaKhuVuc, String TenKhuVuc, String MoTa) {
        this.MaKhuVuc = MaKhuVuc;
        this.TenKhuVuc = TenKhuVuc;
        this.MoTa = MoTa;
    }

    public String getMaKhuVuc() {
        return MaKhuVuc;
    }

    public void setMaKhuVuc(String MaKhuVuc) {
        this.MaKhuVuc = MaKhuVuc;
    }

    public String getTenKhuVuc() {
        return TenKhuVuc;
    }

    public void setTenKhuVuc(String TenKhuVuc) {
        this.TenKhuVuc = TenKhuVuc;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    
    
}
