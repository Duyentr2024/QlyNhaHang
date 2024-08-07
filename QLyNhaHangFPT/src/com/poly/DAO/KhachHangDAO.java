/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.KhachHang;
import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author votha
 */
public class KhachHangDAO extends QuanAn<KhachHang, String> {

    @Override
    public void insert(KhachHang model) {
        String sql = "INSERT INTO KhachHang (HoTenKH, SDT_KH) VALUES (?, ?)";
        XJdbc.update(sql, model.getHoTenKH(), model.getSdtKH());
    }

    @Override
    public void update(KhachHang model) {
        String sql = "UPDATE KhachHang SET HoTenKH=?, SDT_KH=? WHERE MaKH=?";
        XJdbc.update(sql, model.getHoTenKH(), model.getSdtKH(), model.getMaKH());
    }

    @Override
    public void delete(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        XJdbc.update(sql, maKH);
    }

    @Override
    public KhachHang selectById(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH=?";
        List<KhachHang> list = this.selectBySql(sql, maKH);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhachHang> selectAll() {
        String sql = "SELECT * FROM KhachHang";
        return this.selectBySql(sql);
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try (ResultSet rs = XJdbc.query(sql, args)) {
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setHoTenKH(rs.getString("HoTenKH"));
                entity.setSdtKH(rs.getString("SDT_KH"));
                list.add(entity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<String> selectNameKhachHang() {
        String sql = "SELECT HoTenKH FROM KhachHang";
        List<String> names = new ArrayList<>();
        try (ResultSet rs = XJdbc.query(sql)) {
            while (rs.next()) {
                names.add(rs.getString("HoTenKH"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return names;
    }

    // Thêm phương thức getMaKHByHoTen
    public String getMaKHByHoTen(String hoTenKH) {
        String sql = "SELECT MaKH FROM KhachHang WHERE HoTenKH=?";
        try (ResultSet rs = XJdbc.query(sql, hoTenKH)) {
            if (rs.next()) {
                return rs.getString("MaKH");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return null; // Trả về null nếu không tìm thấy mã khách hàng
    }
}
