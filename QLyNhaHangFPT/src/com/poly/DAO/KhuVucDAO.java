package com.poly.DAO;

import com.poly.entity.KhuVuc;
import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhuVucDAO extends QuanAn<KhuVuc, Integer> {

    private final String INSERT_SQL = "INSERT INTO KhuVuc (MaKhuVuc, TenKhuVuc, MoTa) VALUES (?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE KhuVuc SET TenKhuVuc=?, MoTa=? WHERE MaKhuVuc=?";
    private final String DELETE_SQL = "DELETE FROM KhuVuc WHERE MaKhuVuc=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM KhuVuc WHERE MaKhuVuc=?";
    private final String SELECT_ALL_SQL = "SELECT * FROM KhuVuc";
    private final String SELECT_NAME_SQL = "SELECT TenKhuVuc FROM KhuVuc";
    private final String SELECT_ID_SQL = "SELECT MaKhuVuc FROM KhuVuc";  

    @Override
    public void insert(KhuVuc entity) {
        XJdbc.update(INSERT_SQL, entity.getMaKhuVuc(), entity.getTenKhuVuc(), entity.getMoTa());
    }

    @Override
    public void update(KhuVuc entity) {
        XJdbc.update(UPDATE_SQL, entity.getTenKhuVuc(), entity.getMoTa(), entity.getMaKhuVuc());
    }

    @Override
    public void delete(Integer id) {
        XJdbc.update(DELETE_SQL, id);
    }

    @Override
    public KhuVuc selectById(Integer id) {
        List<KhuVuc> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhuVuc> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhuVuc> selectBySql(String sql, Object... args) {
        List<KhuVuc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    KhuVuc entity = new KhuVuc();
                    entity.setMaKhuVuc(rs.getString("MaKhuVuc"));
                    entity.setTenKhuVuc(rs.getString("TenKhuVuc"));
                    entity.setMoTa(rs.getString("MoTa"));
                    list.add(entity);
                }
            } finally {
                if (rs != null && !rs.isClosed()) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> selectNameKhuVuc() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(SELECT_NAME_SQL);
                while (rs.next()) {
                    list.add(rs.getString("TenKhuVuc"));
                }
            } finally {
                if (rs != null && !rs.isClosed()) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
     public List<String> selectIdKhuVuc() {
        List<String> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(SELECT_ID_SQL);
                while (rs.next()) {
                    list.add(rs.getString("MaKhuVuc"));
                }
            } finally {
                if (rs != null && !rs.isClosed()) {
                    rs.getStatement().getConnection().close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
     
}
