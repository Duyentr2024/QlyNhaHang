package com.poly.DAO;

import com.poly.utils.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThongKeDAO {
   
    // General method to retrieve a list of object arrays
     public static List<Object[]>getListOfArray(String sql, String[]col, Object...arg){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = XJdbc.query(sql, arg);
            while (rs.next()) {                
                Object[]vals = new Object[col.length];
                for(int i = 0;i < col.length;i++){
                    vals[i] = rs.getObject(col[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 static String sql_tk_doanhthu = "{CALL sp_ThongKeDoanhThuTuNgayDenNgay(?,?)}";
    // Thống kê doanh thu theo ngày
    public static List<Object[]> getDoangThu(Date startDate, Date endDate) {
//        String sql = "SELECT NgayLap, SUM(TongTien) AS TongDoanhThu " +
//                     "FROM HoaDon " +
//                     "WHERE NgayLap BETWEEN ? AND ? " +
//                     "GROUP BY NgayLap " +
//                     "ORDER BY NgayLap";
        String[] cols = {"NgayLap", "TongDoanhThu"};
        return getListOfArray(sql_tk_doanhthu, cols, startDate, endDate);
    }
    
    private static final String sql_monan_banchay = "{CALL sp_MonAnBanChayNhat()}";
     public List<Object[]> getMonAnBanChayNhat() {
        String[] cols = {"MaMonAn", "TenMonAn", "TongSoLuongBan"};
        return getListOfArray(sql_monan_banchay, cols);
    }
     
     private static final String sql_thongke_tatca = "{CALL sp_ThongKeTatCaMonAn()}";
      public static List<Object[]> loaTatCaMonAn() {
        String[] cols = {"MaMonAn", "TenMonAn", "TongSoLuongBan"};
        return getListOfArray(sql_thongke_tatca, cols);
    }
}
