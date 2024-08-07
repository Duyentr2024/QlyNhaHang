/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.BanAnThongTin;
import com.poly.entity.ChiTietHoaDon;
import com.poly.entity.HoaDon;
import com.poly.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {

    public void insert(ChiTietHoaDon chiTietHoaDon) {
        String sql = "INSERT INTO ChiTietHoaDon (MaHoaDon, MaMonAn, TenMonAn, SoLuong, GiaTien, IsFree) VALUES (?, ?, ?, ?, ?, ?)";
        XJdbc.update(sql,
                chiTietHoaDon.getMaHoaDon() != null ? chiTietHoaDon.getMaHoaDon() : null,
                chiTietHoaDon.getMaMonAn(),
                chiTietHoaDon.getTenMonAn(),
                chiTietHoaDon.getSoLuong(),
                chiTietHoaDon.getGiaTien(),
                chiTietHoaDon.isIsFree()
        );
    }

    public void update(ChiTietHoaDon chiTietHoaDon) {
        String sql = "UPDATE ChiTietHoaDon SET MaHoaDon = ?, MaMonAn = ?, TenMonAn = ?, SoLuong = ?, GiaTien = ?, IsFree = ? WHERE ID_CTHD = ?";
        XJdbc.update(sql,
                chiTietHoaDon.getMaHoaDon() != null ? chiTietHoaDon.getMaHoaDon() : null,
                chiTietHoaDon.getMaMonAn(),
                chiTietHoaDon.getTenMonAn(),
                chiTietHoaDon.getSoLuong(),
                chiTietHoaDon.getGiaTien(),
                chiTietHoaDon.isIsFree(),
                chiTietHoaDon.getIdCTHD()
        );
    }

    public void updateGiaTienAndIsFree(int idCTHD, double giaTien, boolean isFree) {
        String sql = "UPDATE ChiTietHoaDon SET GiaTien = ?, IsFree = ? WHERE ID_CTHD = ?";
        XJdbc.update(sql, giaTien, isFree, idCTHD);
    }

    public void updateQuantityOnly(int idCTHD, int soLuong) {
        String sql = "UPDATE ChiTietHoaDon SET SoLuong = ? WHERE ID_CTHD = ?";
        XJdbc.update(sql, soLuong, idCTHD);
    }

    public void delete(int idCTHD) {
        String sql = "DELETE FROM ChiTietHoaDon WHERE ID_CTHD = ?";
        XJdbc.update(sql, idCTHD);
    }

    public List<ChiTietHoaDon> selectAll() {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";
        try (ResultSet rs = XJdbc.query(sql)) {
            while (rs.next()) {
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(
                        rs.getInt("ID_CTHD"),
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaMonAn"),
                        rs.getString("TenMonAn"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaTien"),
                        rs.getBoolean("IsFree")
                );
                list.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ChiTietHoaDon selectById(int idCTHD) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE ID_CTHD = ?";
        try (ResultSet rs = XJdbc.query(sql, idCTHD)) {
            if (rs.next()) {
                return new ChiTietHoaDon(
                        rs.getInt("ID_CTHD"),
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaMonAn"),
                        rs.getString("TenMonAn"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaTien"),
                        rs.getBoolean("IsFree")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChiTietHoaDon> selectWithNullMaHoaDon() {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaHoaDon IS NULL";
        try (ResultSet rs = XJdbc.query(sql)) {
            while (rs.next()) {
                ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(
                        rs.getInt("ID_CTHD"),
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaMonAn"),
                        rs.getString("TenMonAn"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaTien"),
                        rs.getBoolean("IsFree")
                );
                list.add(chiTietHoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietHoaDon> selectByTenMonAn(String tenMonAn) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE TenMonAn = ?";
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();

        try (ResultSet rs = XJdbc.query(sql, tenMonAn)) {
            while (rs.next()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon(
                        rs.getInt("ID_CTHD"),
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaMonAn"),
                        rs.getString("TenMonAn"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaTien"),
                        rs.getBoolean("IsFree")
                );
                chiTietList.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chiTietList;
    }

    public List<BanAnThongTin> findByThongTinBanAn(String maBanAn) {
        List<BanAnThongTin> resultList = new ArrayList<>();
        String sql = "SELECT "
                + "ba.TenBan, "
                + "cthd.TenMonAn, "
                + "cthd.SoLuong, "
                + "cthd.GiaTien "
                + "FROM HoaDon hd "
                + "JOIN ChiTietHoaDon cthd ON hd.MaHoaDon = cthd.MaHoaDon "
                + "JOIN BanAn ba ON hd.MaBanAn = ba.MaBanAn "
                + "WHERE ba.MaBanAn = ?";

        try (ResultSet rs = XJdbc.query(sql, maBanAn)) {
            while (rs.next()) {
                BanAnThongTin thongTin = new BanAnThongTin(
                        rs.getString("TenBan"),
                        rs.getString("TenMonAn"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaTien")
                );
                resultList.add(thongTin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

     public List<ChiTietHoaDon> selectByMaHoaDon(int maHoaDon) {
        String sql = "SELECT * FROM ChiTietHoaDon WHERE MaHoaDon = ?";
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        try (ResultSet rs = XJdbc.query(sql, maHoaDon)) {
            while (rs.next()) {
                ChiTietHoaDon chiTiet = new ChiTietHoaDon(
                        rs.getInt("ID_CTHD"),
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaMonAn"),
                        rs.getString("TenMonAn"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaTien"),
                        rs.getBoolean("IsFree")
                );
                chiTietList.add(chiTiet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietList;
    }
}
