/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.LoaiMonAn;
import com.poly.entity.MonAn;
import com.poly.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MonAnDAO extends QuanAn<MonAn, Integer> {

    @Override
    public void insert(MonAn model) {
        String sql = "INSERT INTO MonAn (TenMonAn, MaLoaiMon, DVT, DonGia, MoTa, TrangThai, HinhAnhMonAn) VALUES (?, ?, ?, ?, ?, ?, ?)";
        XJdbc.update(sql, model.getTenMonAn(), model.getMaLoaiMon(), model.getDvt(), model.getDonGia(), model.getMoTa(), model.isTrangThai(), model.getHinhAnhMonAn());
    }

    @Override
    public void update(MonAn model) {
        String sql = "UPDATE MonAn SET TenMonAn=?, MaLoaiMon=?, DVT=?, DonGia=?, MoTa=?, TrangThai=?, HinhAnhMonAn=? WHERE MaMonAn=?";
        XJdbc.update(sql, model.getTenMonAn(), model.getMaLoaiMon(), model.getDvt(), model.getDonGia(), model.getMoTa(), model.isTrangThai(), model.getHinhAnhMonAn(), model.getMaMonAn());
    }

    @Override
    public void delete(Integer maMonAn) {
        String sql = "DELETE FROM MonAn WHERE MaMonAn=?";
        XJdbc.update(sql, maMonAn);
    }
    
    public List<MonAn> searchByName(String name) {
        String sql = "SELECT * FROM MonAn WHERE TenMonAn LIKE ?";
        return this.selectBySql(sql, "%" + name + "%");
    }

    @Override
    public MonAn selectById(Integer maMonAn) {
        String sql = "SELECT * FROM MonAn WHERE MaMonAn=?";
        List<MonAn> list = this.selectBySql(sql, maMonAn);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<MonAn> selectAll() {
        String sql = "SELECT * FROM MonAn";
        return this.selectBySql(sql);
    }

    @Override
    protected List<MonAn> selectBySql(String sql, Object... args) {
        List<MonAn> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    MonAn entity = new MonAn();
                    entity.setMaMonAn(rs.getInt("MaMonAn"));
                    entity.setTenMonAn(rs.getString("TenMonAn"));
                    entity.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                    entity.setDvt(rs.getString("DVT"));
                    entity.setDonGia(rs.getDouble("DonGia"));
                    entity.setMoTa(rs.getString("MoTa"));
                    entity.setTrangThai(rs.getBoolean("TrangThai"));
                    entity.setHinhAnhMonAn(rs.getString("HinhAnhMonAn"));
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
     public String getLoaiMonNameById(int maLoaiMon) {
        LoaiMonAnDAO loaiMonAnDAO = new LoaiMonAnDAO();
        LoaiMonAn loaiMonAn = loaiMonAnDAO.selectById(maLoaiMon);
        return loaiMonAn != null ? loaiMonAn.getTenLoaiMon() : "Không xác định";
    }
     public List<MonAn> selectByLoai(int maLoaiMon) {
        String sql = "SELECT * FROM MonAn WHERE MaLoaiMon=?";
        return this.selectBySql(sql, maLoaiMon);
    }
     
}
