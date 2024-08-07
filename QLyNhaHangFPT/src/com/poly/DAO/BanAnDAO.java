package com.poly.DAO;

import com.poly.entity.BanAn;
import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanAnDAO extends QuanAn<BanAn, String> {

    private final String INSERT_SQL = "INSERT INTO BanAn (MaBanAn, TenBan, TrangThai, MaKhuVuc, BanAnGoc) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE BanAn SET TenBan=?, TrangThai=?, MaKhuVuc=?, BanAnGoc=? WHERE MaBanAn=?";
    private final String DELETE_SQL = "DELETE FROM BanAn WHERE MaBanAn=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM BanAn WHERE MaBanAn=?";
    private final String SELECT_ALL_SQL = "SELECT TOP (1000) MaBanAn, TenBan, TrangThai, MaKhuVuc, BanAnGoc FROM BanAn";

    @Override
    public void insert(BanAn entity) {
        XJdbc.update(INSERT_SQL, entity.getMaBanAn(), entity.getTenBan(), entity.isTrangThai(), entity.getMaKhuVuc(), entity.getBanAnGoc());
    }

    @Override
    public void update(BanAn entity) {
        XJdbc.update(UPDATE_SQL, entity.getTenBan(), entity.isTrangThai(), entity.getMaKhuVuc(), entity.getBanAnGoc(), entity.getMaBanAn());
    }

    @Override
    public void delete(String id) {
        XJdbc.update(DELETE_SQL, id);
    }

    @Override
    public BanAn selectById(String id) {
        List<BanAn> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<BanAn> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<BanAn> selectBySql(String sql, Object... args) {
        List<BanAn> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    BanAn entity = new BanAn();
                    entity.setMaBanAn(rs.getString("MaBanAn"));
                    entity.setTenBan(rs.getString("TenBan"));
                    entity.setTrangThai(rs.getBoolean("TrangThai"));
                    entity.setMaKhuVuc(rs.getString("MaKhuVuc"));
                    entity.setBanAnGoc(rs.getString("BanAnGoc"));
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
    private final String SELECT_BY_KHUVUC_SQL = "SELECT * FROM BanAn WHERE MaKhuVuc=?";

    public List<BanAn> selectByKhuVuc(String maKhuVuc) {

        return selectBySql(SELECT_BY_KHUVUC_SQL, maKhuVuc);
    }
}
