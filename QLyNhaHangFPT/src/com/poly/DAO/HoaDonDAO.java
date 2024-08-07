/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.HoaDon;
import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private HoaDon readFromResultSet(ResultSet rs) throws SQLException {
        HoaDon model = new HoaDon();
        model.setMaHoaDon(rs.getInt("MaHoaDon"));
        model.setMaNV(rs.getString("MaNV"));
        model.setMaBanAn(rs.getString("MaBanAn"));
        model.setTongTien(rs.getDouble("TongTien"));
        model.setNgayLap(rs.getDate("NgayLap"));
        return model;
    }

    private List<HoaDon> select(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    list.add(readFromResultSet(rs));
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public HoaDon findLatestHoaDon() {
        String sql = "SELECT TOP 1 * FROM HoaDon ORDER BY MaHoaDon DESC";
        List<HoaDon> list = select(sql);
        return list.size() > 0 ? list.get(0) : null;
    }

    public void insert(HoaDon model) {
        String sql = "INSERT INTO HoaDon (MaNV, MaBanAn, TongTien, NgayLap) VALUES (?, ?, ?, ?)";
        XJdbc.update(sql,
                model.getMaNV(),
                model.getMaBanAn(),
                model.getTongTien(),
                model.getNgayLap()
        );
    }

    public void update(HoaDon model) {
        String sql = "UPDATE HoaDon SET MaNV=?, MaBanAn=?, TongTien=?, NgayLap=? WHERE MaHoaDon=?";
        XJdbc.update(sql,
                model.getMaNV(),
                model.getMaBanAn(),
                model.getTongTien(),
                model.getNgayLap(),
                model.getMaHoaDon()
        );
    }

    public void delete(int maHoaDon) {
        String sql = "DELETE FROM HoaDon WHERE MaHoaDon=?";
        XJdbc.update(sql, maHoaDon);
    }

    public HoaDon findById(int maHoaDon) {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon=?";
        List<HoaDon> list = select(sql, maHoaDon);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<HoaDon> selectAll() {
        String sql = "SELECT * FROM HoaDon";
        return select(sql);
    }

    public List<HoaDon> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM HoaDon WHERE MaNV LIKE ?";
        return select(sql, "%" + keyword + "%");
    }
}
