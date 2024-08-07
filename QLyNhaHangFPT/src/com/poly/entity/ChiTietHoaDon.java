    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package com.poly.entity;

    /**
     *
     * @author duyen
     */
    public class ChiTietHoaDon {
        private int idCTHD;
        private Integer maHoaDon;
        private int maMonAn;
        private String tenMonAn;
        private int soLuong;
        private double giaTien;
        private boolean isFree;

    public ChiTietHoaDon() {
    }

        
        
        
        public ChiTietHoaDon(int idCTHD, Integer maHoaDon, int maMonAn, String tenMonAn, int soLuong, double giaTien, boolean isFree) {
            this.idCTHD = idCTHD;
            this.maHoaDon = maHoaDon;
            this.maMonAn = maMonAn;
            this.tenMonAn = tenMonAn;
            this.soLuong = soLuong;
            this.giaTien = giaTien;
            this.isFree = isFree;
        }

        public int getIdCTHD() {
            return idCTHD;
        }

        public void setIdCTHD(int idCTHD) {
            this.idCTHD = idCTHD;
        }

        public Integer getMaHoaDon() {
            return maHoaDon;
        }

        public void setMaHoaDon(Integer maHoaDon) {
            this.maHoaDon = maHoaDon;
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

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public double getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(double giaTien) {
            this.giaTien = giaTien;
        }

        public boolean isIsFree() {
            return isFree;
        }

        public void setIsFree(boolean isFree) {
            this.isFree = isFree;
        }

   

    }
