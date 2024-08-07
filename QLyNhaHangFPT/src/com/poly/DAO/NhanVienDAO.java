package com.poly.DAO;

import com.poly.entity.NhanVien;
import com.poly.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO extends QuanAn<NhanVien, String> {

    @Override
    public void insert(NhanVien entity) {
        String sql = "INSERT INTO NhanVien (MaNV, HoTenNV, GioiTinh, NgaySinh, SDT_NV, CCCD, DiaChi, NgayVaoLam, TrangThai, HinhAnh_NV, email, BoPhan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        XJdbc.update(sql,
                entity.getMaNV(),
                entity.getHoTenNV(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getSdtNV(),
                entity.getCccd(),
                entity.getDiaChi(),
                entity.getNgayVaoLam(),
                entity.isTrangThai(),
                entity.getHinhAnhNV(),
                entity.getEmail(),
                entity.getBoPhan());
    }

    @Override
    public void update(NhanVien entity) {
        String sql = "UPDATE NhanVien SET HoTenNV=?, GioiTinh=?, NgaySinh=?, SDT_NV=?, CCCD=?, DiaChi=?, NgayVaoLam=?, TrangThai=?, HinhAnh_NV=?, email=?, BoPhan=? WHERE MaNV=?";
        XJdbc.update(sql,
                entity.getHoTenNV(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getSdtNV(),
                entity.getCccd(),
                entity.getDiaChi(),
                entity.getNgayVaoLam(),
                entity.isTrangThai(),
                entity.getHinhAnhNV(),
                entity.getEmail(),
                entity.getBoPhan(),
                entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        XJdbc.update(sql, id);
    }

    @Override
    public NhanVien selectById(String id) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = this.selectBySql(sql, id);
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
                    entity.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                    entity.setTrangThai(rs.getBoolean("TrangThai"));
                    entity.setHinhAnhNV(rs.getString("HinhAnh_NV"));
                    entity.setEmail(rs.getString("email"));
                    entity.setBoPhan(rs.getString("BoPhan"));
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

    // New method to select by keyword
    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV LIKE ? OR HoTenNV LIKE ? OR SDT_NV LIKE ?";
        keyword = "%" + keyword + "%";
        return this.selectBySql(sql, keyword, keyword, keyword);
    }
    
    
}
