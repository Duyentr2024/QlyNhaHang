/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.LoaiMonAn;
import com.poly.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO extends QuanAn<LoaiMonAn, Integer> {


    @Override
    public void insert(LoaiMonAn model) {
        String sql = "INSERT INTO LoaiMon (TenLoaiMon) VALUES (?)";
        XJdbc.update(sql, model.getTenLoaiMon());
    }

    @Override
    public void update(LoaiMonAn model) {
        String sql = "UPDATE LoaiMon SET TenLoaiMon=? WHERE MaLoaiMon=?";
        XJdbc.update(sql, model.getTenLoaiMon(), model.getMaLoaiMon());
    }

    @Override
    public void delete(Integer maLoaiMon) {
        String sql = "DELETE FROM LoaiMon WHERE MaLoaiMon=?";
        XJdbc.update(sql, maLoaiMon);
    }

    @Override
    public LoaiMonAn selectById(Integer maLoaiMon) {
        String sql = "SELECT * FROM LoaiMon WHERE MaLoaiMon=?";
        List<LoaiMonAn> list = this.selectBySql(sql, maLoaiMon);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    @Override
    public List<LoaiMonAn> selectAll() {
        String sql = "SELECT * FROM LoaiMon";
        return this.selectBySql(sql);
    }

    @Override
    protected List<LoaiMonAn> selectBySql(String sql, Object... args) {
        List<LoaiMonAn> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    LoaiMonAn entity = new LoaiMonAn();
                    entity.setMaLoaiMon(rs.getInt("MaLoaiMon"));
                    entity.setTenLoaiMon(rs.getString("TenLoaiMon"));
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
}
