/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.NhanVien;
import com.poly.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO extends QuanAn<NhanVien, String> {

    @Override
    public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien (MaNV, HoTenNV, GioiTinh, NgaySinh, SDT_NV, CCCD, DiaChi, ChucVu, NgayVaoLam, TrangThai, HinhAnh_NV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        XJdbc.update(sql, model.getMaNV(), model.getHoTenNV(), model.isGioiTinh(), model.getNgaySinh(), model.getSdtNV(), model.getCccd(), model.getDiaChi(), model.isChucVu(), model.getNgayVaoLam(), model.isTrangThai(), model.getHinhAnhNV());
    }

    @Override
    public void update(NhanVien model) {
        String sql = "UPDATE NhanVien SET HoTenNV=?, GioiTinh=?, NgaySinh=?, SDT_NV=?, CCCD=?, DiaChi=?, ChucVu=?, TrangThai=?, HinhAnh_NV=? WHERE MaNV=?";
        XJdbc.update(sql, model.getHoTenNV(), model.isGioiTinh(), model.getNgaySinh(), model.getSdtNV(), model.getCccd(), model.getDiaChi(), model.isChucVu(), model.isTrangThai(), model.getHinhAnhNV(), model.getMaNV());
    }

    @Override
    public void delete(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        XJdbc.update(sql, maNV);
    }

    @Override
    public NhanVien selectById(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = this.selectBySql(sql, maNV);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<NhanVien> selectAll() {
        String sql = "SELECT * FROM NhanVien";
        return this.selectBySql(sql);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    NhanVien entity = new NhanVien();
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setHoTenNV(rs.getString("HoTenNV"));
                    entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                    entity.setNgaySinh(rs.getDate("NgaySinh"));
                    entity.setSdtNV(rs.getString("SDT_NV"));
                    entity.setCccd(rs.getString("CCCD"));
                    entity.setDiaChi(rs.getString("DiaChi"));
                    entity.setChucVu(rs.getBoolean("ChucVu"));
                    entity.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                    entity.setTrangThai(rs.getBoolean("TrangThai"));
                    entity.setHinhAnhNV(rs.getString("HinhAnh_NV"));
                    list.add(entity);
                }
            } finally {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }
    
        public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE HoTenNV LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }
}
