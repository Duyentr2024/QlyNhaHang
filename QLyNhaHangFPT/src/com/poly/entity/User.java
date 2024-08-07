/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.entity;

/**
 *
 * @author Admin
 */
public class User {
     private String maNV;      
    private String matKhau;
    private Boolean capQuyen;

    public User() {
    }

    public User(String maNV, String matKhau, Boolean capQuyen) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.capQuyen = capQuyen;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isCapQuyen() {
        return capQuyen;
    }


    public void setCapQuyen(Boolean capQuyen) {
        this.capQuyen = capQuyen;
    }
    
    
}
