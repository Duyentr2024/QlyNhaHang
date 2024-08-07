package com.poly.DAO;

import com.poly.entity.User;
import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends QuanAn<User, String> {

    @Override
    public void insert(User entity) {
        String sql = "INSERT INTO Users (MaNV, MatKhau, CapQuyen) VALUES (?, ?, ?)";
        XJdbc.update(sql,
                entity.getMaNV(),
                entity.getMatKhau(),
                entity.isCapQuyen());
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE Users SET MatKhau=?, CapQuyen=? WHERE MaNV=?";
        XJdbc.update(sql,
                entity.getMatKhau(),
                entity.isCapQuyen(),
                entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Users WHERE MaNV=?";
        XJdbc.update(sql, id);
    }

    @Override
    public User selectById(String id) {
        String sql = "SELECT * FROM Users WHERE MaNV=?";
        List<User> list = this.selectBySql(sql, id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<User> selectAll() {
        String sql = "SELECT * FROM Users";
        return this.selectBySql(sql);
    }

    @Override
    protected List<User> selectBySql(String sql, Object... args) {
        List<User> list = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.query(sql, args);
            while (rs.next()) {
                User entity = new User();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setCapQuyen(rs.getBoolean("CapQuyen"));
                list.add(entity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.getStatement().getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    // New method to select by keyword
    public List<User> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM Users WHERE MaNV LIKE ? OR MatKhau LIKE ?";
        keyword = "%" + keyword + "%";
        return this.selectBySql(sql, keyword, keyword);
    }
    
    // Updated method to update password
    public void updatePassword(String maNV, String newPassword) {
        String sql = "UPDATE Users SET MatKhau = ? WHERE MaNV = ?";
        XJdbc.update(sql, newPassword, maNV);
    }
}
