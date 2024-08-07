package com.poly.DAO;

import com.poly.entity.BanAn;
import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanAnDAO extends QuanAn<BanAn, String> {

    @Override
    public void insert(BanAn model) {
        String sql = "INSERT INTO BanAn (MaBanAn, TenBan, MaKhuVuc) VALUES (?, ?, ?)";
        XJdbc.update(sql, model.getMaBanAn(), model.getTenBan(), model.getMaKhuVuc());
    }

    @Override
    public void update(BanAn model) {
        String sql = "UPDATE BanAn SET TenBan=?, MaKhuVuc=? WHERE MaBanAn=?";
        XJdbc.update(sql, model.getTenBan(), model.getMaKhuVuc(), model.getMaBanAn());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM BanAn WHERE MaBanAn=?";
        XJdbc.update(sql, id);
    }

    @Override
    public BanAn selectById(String id) {
        String sql = "SELECT * FROM BanAn WHERE MaBanAn=?";
        List<BanAn> list = this.selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<BanAn> selectAll() {
        String sql = "SELECT * FROM BanAn";
        return this.selectBySql(sql);
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
                    entity.setMaKhuVuc(rs.getString("MaKhuVuc"));
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
