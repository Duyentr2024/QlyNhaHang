package com.poly.DAO;

import com.poly.entity.ChiTietDacBan;
import com.poly.utils.XJdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDacBanDAO extends QuanAn<ChiTietDacBan, String> {

    private final String INSERT_SQL = "INSERT INTO ChiTietDatBan (MaKH, MaBanAn, NgayDatBan, TienCoc, MoTa) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE ChiTietDatBan SET MaBanAn=?, NgayDatBan=?, TienCoc=?, MoTa=? WHERE MaKH=?";
    private final String DELETE_SQL = "DELETE FROM ChiTietDatBan WHERE MaKH=?";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietDatBan WHERE MaKH=?";
    private final String SELECT_ALL_SQL = "SELECT TOP (1000) MaKH, MaBanAn, NgayDatBan, TienCoc, MoTa FROM ChiTietDatBan";
    private final String SELECT_BY_MA_BAN_AN_SQL = "SELECT * FROM ChiTietDatBan WHERE MaBanAn=?";
    
    @Override
    public void insert(ChiTietDacBan chiTietDacBan) {
        XJdbc.update(INSERT_SQL,
                chiTietDacBan.getMaKH(),         // Thêm MaKH vào câu lệnh INSERT
                chiTietDacBan.getMaBanAn(),      // MaBanAn
                chiTietDacBan.getNgayDatBan(),   // NgayDatBan
                chiTietDacBan.getTienCoc(),      // TienCoc
                chiTietDacBan.getMoTa()          // MoTa
        );
    }

    @Override
    public void update(ChiTietDacBan entity) {
        XJdbc.update(UPDATE_SQL,
                entity.getMaBanAn(),      // MaBanAn
                entity.getNgayDatBan(),   // NgayDatBan
                entity.getTienCoc(),      // TienCoc
                entity.getMoTa(),         // MoTa
                entity.getMaKH()          // MaKH
        );
    }

    @Override
    public void delete(String id) {
        XJdbc.update(DELETE_SQL, id);
    }

    @Override
    public ChiTietDacBan selectById(String id) {
        List<ChiTietDacBan> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChiTietDacBan> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChiTietDacBan> selectBySql(String sql, Object... args) {
        List<ChiTietDacBan> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = XJdbc.query(sql, args);
                while (rs.next()) {
                    ChiTietDacBan entity = new ChiTietDacBan();
                    entity.setMaKH(rs.getString("MaKH")); // Thêm MaKH
                    entity.setMaBanAn(rs.getString("MaBanAn"));
                    entity.setNgayDatBan(rs.getString("NgayDatBan"));
                    entity.setTienCoc(rs.getDouble("TienCoc"));
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

    public ChiTietDacBan selectByMaBanAn(String maBanAn) {
        List<ChiTietDacBan> list = selectBySql(SELECT_BY_MA_BAN_AN_SQL, maBanAn);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0); // Return the first result or handle accordingly if you expect multiple results
    }

   public void deleteByMaBanAn(String maBanAn) {
    // Câu lệnh SQL để xóa thông tin dựa trên mã bàn ăn
    String deleteSql = "DELETE FROM ChiTietDatBan WHERE MaBanAn=?";
    
    // Thực thi câu lệnh xóa
    XJdbc.update(deleteSql, maBanAn);
}

}
