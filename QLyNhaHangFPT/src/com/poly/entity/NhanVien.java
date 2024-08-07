package com.poly.entity;

import com.poly.utils.XDate;
import java.util.Date;

public class NhanVien {

    private String maNV;
    private String hoTenNV;
    private boolean gioiTinh;
    private Date ngaySinh = XDate.addDays(new Date(), -365 * 20);
    private String sdtNV;
    private String cccd;
    private String diaChi;
    private Date ngayVaoLam;
    private boolean trangThai;
    private String hinhAnhNV;
    private String email;
    private String boPhan;

    public NhanVien() {
    }

    public NhanVien(String maNV, String hoTenNV, boolean gioiTinh, Date ngaySinh, String sdtNV, String cccd, String diaChi, Date ngayVaoLam, boolean trangThai, String hinhAnhNV, String email, String boPhan) {
        this.maNV = maNV;
        this.hoTenNV = hoTenNV;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdtNV = sdtNV;
        this.cccd = cccd;
        this.diaChi = diaChi;
        this.ngayVaoLam = ngayVaoLam;
        this.trangThai = trangThai;
        this.hinhAnhNV = hinhAnhNV;
        this.email = email;
        this.boPhan = boPhan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdtNV() {
        return sdtNV;
    }

    public void setSdtNV(String sdtNV) {
        this.sdtNV = sdtNV;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnhNV() {
        return hinhAnhNV;
    }

    public void setHinhAnhNV(String hinhAnhNV) {
        this.hinhAnhNV = hinhAnhNV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBoPhan() {
        return boPhan;
    }

    public void setBoPhan(String boPhan) {
        this.boPhan = boPhan;
    }

    
}
