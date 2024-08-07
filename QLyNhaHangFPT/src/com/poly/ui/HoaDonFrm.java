/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.poly.ui;

import com.poly.DAO.ChiTietHoaDonDAO;
import com.poly.DAO.HoaDonDAO;
import com.poly.DAO.LoaiMonAnDAO;
import com.poly.DAO.MonAnDAO;
import com.poly.entity.ChiTietHoaDon;
import com.poly.entity.HoaDon;
import com.poly.entity.LoaiMonAn;
import com.poly.entity.MonAn;
import com.poly.utils.ExcelExporter;
import com.poly.utils.InvoicePrinter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author duyen
 */
public class HoaDonFrm extends javax.swing.JDialog {

    /**
     * Creates new form HoaDonFrm
     */
    public HoaDonFrm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        getDate();
        loadTableData();
        loadMonAnPanelsByLoai();
        updateTabs();
        loadChiTietHoaDonToTable();
        loadHoaDonToTable();
        updateButtonState();
        btnInLai.setEnabled(false);
        btnXuatExcel.setEnabled(false);
    }

    private void getDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(currentDate);
        lblNgay.setText(formattedDate);
    }

    private void loadTableData() {
        MonAnDAO monAnDAO = new MonAnDAO();
        List<MonAn> list = monAnDAO.selectAll();

        DefaultTableModel model = (DefaultTableModel) tblMonAn.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ nếu có
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        for (MonAn monAn : list) {
            Object[] row = new Object[]{
                monAn.getTenMonAn(),
                monAnDAO.getLoaiMonNameById(monAn.getMaLoaiMon()), // Lấy tên loại món
                monAn.getDvt(),
                currencyFormat.format(monAn.getDonGia())
            };
            model.addRow(row);
        }
    }

    private void loadMonAnPanelsByLoai() {
        LoaiMonAnDAO loaiMonAnDAO = new LoaiMonAnDAO();
        MonAnDAO monAnDAO = new MonAnDAO();
        List<LoaiMonAn> loaiMonAnList = loaiMonAnDAO.selectAll();
        while (tblP1.getTabCount() > 1) {
            tblP1.remove(0);
        }
        for (LoaiMonAn loaiMonAn : loaiMonAnList) {
            JPanel panelGrid = new JPanel();
            panelGrid.setLayout(new GridLayout(0, 4, 10, 10)); // 4 columns, flexible rows, 10px gap
            panelGrid.removeAll();
            List<MonAn> monAnList = monAnDAO.selectByLoai(loaiMonAn.getMaLoaiMon());
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            for (MonAn monAn : monAnList) {
                ImageIcon icon = new ImageIcon(monAn.getHinhAnhMonAn());
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);
                JLabel lblAnhMonAn = new JLabel(icon);
                lblAnhMonAn.setHorizontalAlignment(JLabel.CENTER);

                JPanel pnlMonAnItem = new JPanel();
                pnlMonAnItem.setLayout(new BorderLayout());
                pnlMonAnItem.add(lblAnhMonAn, BorderLayout.CENTER);

                JPanel pnlText = new JPanel();
                pnlText.setLayout(new BoxLayout(pnlText, BoxLayout.Y_AXIS));
                pnlText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                JLabel lblTenMonAn = new JLabel(monAn.getTenMonAn());
                lblTenMonAn.setAlignmentX(Component.CENTER_ALIGNMENT);
                lblTenMonAn.setFont(lblTenMonAn.getFont().deriveFont(Font.BOLD));

                JLabel lblGiaMonAn = new JLabel(currencyFormat.format(monAn.getDonGia()));
                lblGiaMonAn.setAlignmentX(Component.CENTER_ALIGNMENT);
                lblGiaMonAn.setFont(lblGiaMonAn.getFont().deriveFont(Font.BOLD));

                pnlText.add(lblTenMonAn);
                pnlText.add(lblGiaMonAn);

                pnlMonAnItem.add(pnlText, BorderLayout.SOUTH);
                pnlMonAnItem.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                pnlMonAnItem.setPreferredSize(new Dimension(150, 150)); // Set the size of the panel

                pnlMonAnItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showQuantityDialog(monAn);
                    }
                });
                panelGrid.add(pnlMonAnItem);
            }
            JScrollPane scrollPane = new JScrollPane(panelGrid);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            panelGrid.setPreferredSize(new Dimension(400, 600));
            tblP1.insertTab(loaiMonAn.getTenLoaiMon(), null, scrollPane, null, tblP1.getTabCount() - 1);
        }
        tblP1.revalidate();
        tblP1.repaint();
    }

    private void showQuantityDialog(MonAn monAn) {
        String quantityStr = JOptionPane.showInputDialog(null, "Nhập số lượng cho món ăn: " + monAn.getTenMonAn(), "Nhập số lượng", JOptionPane.PLAIN_MESSAGE);
        if (quantityStr != null && !quantityStr.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > 0) {
                    DefaultTableModel addModel = (DefaultTableModel) tblChiTietHoaDon1.getModel();
                    ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
                    boolean itemExists = false;
                    for (int i = 0; i < addModel.getRowCount(); i++) {
                        String tenMonAn = (String) addModel.getValueAt(i, 0);
                        if (tenMonAn.equals(monAn.getTenMonAn())) {
                            int currentQuantity = (int) addModel.getValueAt(i, 2);
                            int newQuantity = currentQuantity + quantity;
                            double unitPrice = monAn.getDonGia();
                            double totalPrice = unitPrice * newQuantity;
                            addModel.setValueAt(newQuantity, i, 2);
                            addModel.setValueAt(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalPrice), i, 3);
                            ChiTietHoaDon chiTietHoaDon = chiTietHoaDonDAO.selectByTenMonAn(monAn.getTenMonAn());
                            chiTietHoaDon.setSoLuong(newQuantity);
                            chiTietHoaDon.setMaHoaDon(null);  // Đảm bảo MaHoaDon luôn là null
                            chiTietHoaDonDAO.update(chiTietHoaDon);

                            itemExists = true;
                            break;
                        }
                    }
                    if (!itemExists) {
                        double unitPrice = monAn.getDonGia();
                        double totalPrice = unitPrice * quantity;
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        addModel.addRow(new Object[]{
                            monAn.getTenMonAn(),
                            currencyFormat.format(unitPrice),
                            quantity,
                            currencyFormat.format(totalPrice)
                        });
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(
                                0,
                                null, // Đảm bảo MaHoaDon luôn là null
                                monAn.getMaMonAn(),
                                monAn.getTenMonAn(),
                                quantity,
                                unitPrice,
                                false
                        );
                        chiTietHoaDonDAO.insert(chiTietHoaDon);
                    }

                    tblChiTietHoaDon1.revalidate();
                    tblChiTietHoaDon1.repaint();
                    JOptionPane.showMessageDialog(null, "Món ăn đã được thêm vào hóa đơn.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    loadChiTietHoaDonToTable();
                } else {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTabs() {
        LoaiMonAnDAO loaiMonAnDAO = new LoaiMonAnDAO();
        List<LoaiMonAn> loaiMonAnList = loaiMonAnDAO.selectAll();
        for (int i = 0; i < loaiMonAnList.size() && i < tblP1.getTabCount() - 1; i++) {
            tblP1.setTitleAt(i, loaiMonAnList.get(i).getTenLoaiMon());
        }
        tblP1.setTitleAt(tblP1.getTabCount() - 1, "Tất cả");
    }

    private void loadChiTietHoaDonToTable() {
        try {
            ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
            List<ChiTietHoaDon> list = chiTietHoaDonDAO.selectWithNullMaHoaDon();
            DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon1.getModel();
            model.setRowCount(0);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            double totalAmount = 0;
            for (ChiTietHoaDon item : list) {
                double thanhTien = item.getGiaTien() * item.getSoLuong();
                totalAmount += thanhTien;
                model.addRow(new Object[]{
                    item.getTenMonAn(),
                    currencyFormat.format(item.getGiaTien()),
                    item.getSoLuong(),
                    currencyFormat.format(thanhTien),});
            }
            lblTamTinh.setText(currencyFormat.format(totalAmount));
            lblTongCong.setText(currencyFormat.format(totalAmount));
            updateButtonState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateButtonState() {
        String tongCongText = lblTongCong.getText().replaceAll("[^\\d]", "");
        btnThanhToan.setEnabled(!tongCongText.isEmpty());
    }

    private void loadHoaDonToTable() {
        try {
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            List<HoaDon> list = hoaDonDAO.selectAll();
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Định dạng tiền tệ Việt Nam Đồng (VNĐ)
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
            formatSymbols.setCurrencySymbol("₫");
            ((DecimalFormat) currencyFormat).setDecimalFormatSymbols(formatSymbols);

            for (HoaDon hoaDon : list) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    currencyFormat.format(hoaDon.getTongTien()),
                    dateFormat.format(hoaDon.getNgayLap())
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
        }
    }

    private void updateItemToFree(int selectedRow) {
        if (selectedRow >= 0) {
            String tenMonAn = (String) tblChiTietHoaDon1.getValueAt(selectedRow, 0);
            DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon1.getModel();
            double unitPrice = 0;
            int quantity = (int) model.getValueAt(selectedRow, 2);
            double totalPrice = unitPrice * quantity;
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            model.setValueAt(currencyFormat.format(unitPrice), selectedRow, 1);
            model.setValueAt(currencyFormat.format(totalPrice), selectedRow, 3);
            ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
            ChiTietHoaDon chiTietHoaDon = chiTietHoaDonDAO.selectByTenMonAn(tenMonAn);
            if (chiTietHoaDon != null) {
                chiTietHoaDonDAO.updateGiaTienAndIsFree(chiTietHoaDon.getIdCTHD(), unitPrice, true);
            }
            JOptionPane.showMessageDialog(null, "Món ăn đã được tặng miễn phí.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadChiTietHoaDonToTable();
            btnPhanTram.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn cần tặng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateQuantity(int selectedRow) {
        if (selectedRow >= 0) {
            String tenMonAn = (String) tblChiTietHoaDon1.getValueAt(selectedRow, 0);
            int currentQuantity = (int) tblChiTietHoaDon1.getValueAt(selectedRow, 2);
            String quantityStr = JOptionPane.showInputDialog(null, "Nhập số lượng mới cho món ăn: " + tenMonAn, "Cập nhật số lượng", JOptionPane.PLAIN_MESSAGE);
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int newQuantity = Integer.parseInt(quantityStr);
                    if (newQuantity > 0) {
                        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon1.getModel();
                        double unitPrice = parseCurrency((String) model.getValueAt(selectedRow, 1));
                        double totalPrice = unitPrice * newQuantity;
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        model.setValueAt(newQuantity, selectedRow, 2);
                        model.setValueAt(currencyFormat.format(totalPrice), selectedRow, 3);
                        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
                        ChiTietHoaDon chiTietHoaDon = chiTietHoaDonDAO.selectByTenMonAn(tenMonAn);
                        if (chiTietHoaDon != null) {
                            chiTietHoaDon.setSoLuong(newQuantity);
                            chiTietHoaDonDAO.updateQuantityOnly(chiTietHoaDon.getIdCTHD(), newQuantity);
                        }
                        JOptionPane.showMessageDialog(null, "Đã cập nhật số lượng món ăn.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadChiTietHoaDonToTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0 !", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn cần cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseCurrency(String currencyStr) {
        try {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            Number number = currencyFormat.parse(currencyStr);
            return number.doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void applyDiscount() {
        String percentStr = JOptionPane.showInputDialog(null, "Nhập phần trăm giảm giá (5% - 15%)", "Giảm giá", JOptionPane.PLAIN_MESSAGE);
        if (percentStr != null && !percentStr.isEmpty()) {
            try {
                int percent = Integer.parseInt(percentStr);
                if (percent >= 5 && percent <= 15) {
                    String totalStr = lblTamTinh.getText();
                    double totalAmount = parseCurrency(totalStr);
                    double discountAmount = totalAmount * percent / 100;
                    double finalAmount = totalAmount - discountAmount;
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    lblTongCong.setText(currencyFormat.format(finalAmount));
                    lblPhanTram.setText(percent + "%");
                    JOptionPane.showMessageDialog(null, "Đã áp dụng giảm giá " + percent + "%.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    btnTang.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Phần trăm giảm giá phải trong khoảng 5% - 15%.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadChiTietHoaDonByMaHoaDon(int maHoaDon) {
        try {
            ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
            List<ChiTietHoaDon> list = chiTietHoaDonDAO.selectByMaHoaDon(maHoaDon);
            DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet2.getModel();
            model.setRowCount(0);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            for (ChiTietHoaDon chiTiet : list) {
                double thanhTien = chiTiet.getGiaTien() * chiTiet.getSoLuong();
                model.addRow(new Object[]{
                    chiTiet.getMaHoaDon(),
                    chiTiet.getTenMonAn(),
                    currencyFormat.format(chiTiet.getGiaTien()),
                    chiTiet.getSoLuong(),
                    currencyFormat.format(thanhTien)
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu chi tiết hóa đơn: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JPanel = new javax.swing.JPanel();
        tblP1 = new javax.swing.JTabbedPane();
        pnlMonAn = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        txtTimKiemMonAn = new javax.swing.JTextField();
        btnTimKiem1 = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMonAn = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel19 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        lblNgay = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTamTinh = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        btnCapNhatSoLuong = new javax.swing.JButton();
        btnTang = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietHoaDon1 = new javax.swing.JTable();
        jPanel22 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        btnThanhToan = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtKhachDua = new javax.swing.JTextField();
        txtTraKhach = new javax.swing.JTextField();
        cboPhuongThucThanhToan = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        btnPhanTram = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        lblDatCoc = new javax.swing.JLabel();
        lblPhanTram = new javax.swing.JLabel();
        lblTongCong = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        txtTimKiem2 = new javax.swing.JTextField();
        btnTimKiem2 = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel39 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        btnInLai = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();
        btnLamMoiHoaDon = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        JPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THỰC ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        JPanel.setLayout(new java.awt.BorderLayout());

        tblP1.setPreferredSize(new java.awt.Dimension(468, 468));

        pnlMonAn.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab1", pnlMonAn);

        jPanel4.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab2", jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab3", jPanel5);

        jPanel6.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab4", jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(1, 4));
        tblP1.addTab("tab5", jPanel7);

        jPanel8.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab6", jPanel8);

        jPanel9.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab7", jPanel9);

        jPanel10.setLayout(new java.awt.GridLayout(0, 4));
        tblP1.addTab("tab8", jPanel10);

        jPanel11.setLayout(new java.awt.BorderLayout());

        btnTimKiem1.setText("Tìm kiếm");
        btnTimKiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiem1ActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemMonAn, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTimKiem1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLamMoi)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemMonAn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem1)
                    .addComponent(btnLamMoi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel13.setLayout(new java.awt.BorderLayout());

        tblMonAn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên món", "Loại món", "ĐVT", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMonAn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMonAnMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMonAn);
        if (tblMonAn.getColumnModel().getColumnCount() > 0) {
            tblMonAn.getColumnModel().getColumn(0).setResizable(false);
            tblMonAn.getColumnModel().getColumn(1).setResizable(false);
            tblMonAn.getColumnModel().getColumn(2).setResizable(false);
            tblMonAn.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel13.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel13, java.awt.BorderLayout.CENTER);

        tblP1.addTab("Tất cả", jPanel11);

        JPanel.add(tblP1, java.awt.BorderLayout.CENTER);

        getContentPane().add(JPanel);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HÓA ĐƠN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 600));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jTabbedPane2.setPreferredSize(new java.awt.Dimension(415, 495));

        jPanel19.setLayout(new java.awt.BorderLayout());

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel21.setPreferredSize(new java.awt.Dimension(485, 200));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel20.setLayout(new java.awt.BorderLayout());

        lblNgay.setText("dd/MM/yyyy");
        jPanel26.add(lblNgay);

        jPanel20.add(jPanel26, java.awt.BorderLayout.LINE_END);

        jLabel3.setText("Khách:");
        jPanel27.add(jLabel3);

        jLabel4.setText("name bàn");
        jPanel27.add(jLabel4);

        jPanel20.add(jPanel27, java.awt.BorderLayout.LINE_START);

        jPanel21.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        jPanel24.setLayout(new java.awt.BorderLayout());

        jLabel5.setText("Tạm tính:");
        jPanel28.add(jLabel5);

        lblTamTinh.setText("0000");
        jPanel28.add(lblTamTinh);

        jPanel24.add(jPanel28, java.awt.BorderLayout.LINE_END);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel29.add(btnXoa);

        btnCapNhatSoLuong.setText("Cập nhật");
        btnCapNhatSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatSoLuongActionPerformed(evt);
            }
        });
        jPanel29.add(btnCapNhatSoLuong);

        btnTang.setText("Tặng");
        btnTang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTangActionPerformed(evt);
            }
        });
        jPanel29.add(btnTang);

        jPanel24.add(jPanel29, java.awt.BorderLayout.LINE_START);

        jPanel21.add(jPanel24, java.awt.BorderLayout.PAGE_END);

        jPanel25.setLayout(new java.awt.BorderLayout());

        tblChiTietHoaDon1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tên món", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblChiTietHoaDon1);
        if (tblChiTietHoaDon1.getColumnModel().getColumnCount() > 0) {
            tblChiTietHoaDon1.getColumnModel().getColumn(0).setResizable(false);
            tblChiTietHoaDon1.getColumnModel().getColumn(1).setResizable(false);
            tblChiTietHoaDon1.getColumnModel().getColumn(2).setResizable(false);
            tblChiTietHoaDon1.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel25.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel25, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel21, java.awt.BorderLayout.PAGE_START);

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jPanel34.setLayout(new java.awt.GridLayout(1, 0));

        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel34.add(btnThanhToan);

        jPanel22.add(jPanel34, java.awt.BorderLayout.LINE_END);

        jLabel12.setText("Phương thức thanh toán");

        jLabel13.setText("Khách đưa");

        jLabel14.setText("Trả lại");

        cboPhuongThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thanh Toán Tiền Mặt", "Thanh Toán Bằng Thẻ" }));
        cboPhuongThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPhuongThucThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKhachDua)
                    .addComponent(txtTraKhach)
                    .addComponent(cboPhuongThucThanhToan, 0, 291, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cboPhuongThucThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTraKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel22.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel22, java.awt.BorderLayout.PAGE_END);

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        jPanel23.setPreferredSize(new java.awt.Dimension(485, 100));
        jPanel23.setLayout(new java.awt.BorderLayout());

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.setLayout(new java.awt.GridLayout(0, 1));

        jButton6.setText("Lưu");
        jPanel30.add(jButton6);

        btnPhanTram.setText("%");
        btnPhanTram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhanTramActionPerformed(evt);
            }
        });
        jPanel30.add(btnPhanTram);

        jButton8.setText("Quay lại");
        jPanel30.add(jButton8);

        jPanel23.add(jPanel30, java.awt.BorderLayout.LINE_START);

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel31.setLayout(new java.awt.BorderLayout());

        jPanel32.setLayout(new java.awt.GridLayout(0, 1));

        lblDatCoc.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDatCoc.setText("0");
        jPanel32.add(lblDatCoc);

        lblPhanTram.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblPhanTram.setText("0");
        jPanel32.add(lblPhanTram);

        lblTongCong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongCong.setText("0000");
        jPanel32.add(lblTongCong);

        jPanel31.add(jPanel32, java.awt.BorderLayout.LINE_END);

        jPanel33.setLayout(new java.awt.GridLayout(0, 1));

        jLabel9.setText("Chiết khấu/Đặt cọc:");
        jPanel33.add(jLabel9);

        jLabel10.setText("Khuyến mãi:");
        jPanel33.add(jLabel10);

        jLabel11.setText("Tổng cộng");
        jPanel33.add(jLabel11);

        jPanel31.add(jPanel33, java.awt.BorderLayout.LINE_START);

        jPanel23.add(jPanel31, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel23, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Hóa đơn", jPanel19);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel36.setLayout(new java.awt.BorderLayout());
        jPanel18.add(jPanel36, java.awt.BorderLayout.PAGE_START);

        jPanel37.setLayout(new java.awt.BorderLayout());

        jPanel38.setPreferredSize(new java.awt.Dimension(485, 250));
        jPanel38.setLayout(new java.awt.BorderLayout());

        btnTimKiem2.setText("Tìm kiếm");
        btnTimKiem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiem2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem2)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel38.add(jPanel40, java.awt.BorderLayout.PAGE_START);

        jPanel41.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Hóa đơn", "Tổng tiền", "Ngày lập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDon.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel41.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel38.add(jPanel41, java.awt.BorderLayout.CENTER);

        jPanel37.add(jPanel38, java.awt.BorderLayout.PAGE_START);

        jPanel39.setLayout(new java.awt.BorderLayout());

        jPanel42.setLayout(new java.awt.GridLayout(1, 0));

        btnInLai.setText("In lại");
        btnInLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInLaiActionPerformed(evt);
            }
        });
        jPanel42.add(btnInLai);

        btnXuatExcel.setText("Xuất ecxel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jPanel42.add(btnXuatExcel);

        btnLamMoiHoaDon.setText("Làm mới");
        btnLamMoiHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiHoaDonActionPerformed(evt);
            }
        });
        jPanel42.add(btnLamMoiHoaDon);

        jButton14.setText("Exit");
        jPanel42.add(jButton14);

        jPanel39.add(jPanel42, java.awt.BorderLayout.PAGE_END);

        jPanel43.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết hóa đơn", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblHoaDonChiTiet2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Tên món", "Đơn giá", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblHoaDonChiTiet2);
        if (tblHoaDonChiTiet2.getColumnModel().getColumnCount() > 0) {
            tblHoaDonChiTiet2.getColumnModel().getColumn(0).setResizable(false);
            tblHoaDonChiTiet2.getColumnModel().getColumn(1).setResizable(false);
            tblHoaDonChiTiet2.getColumnModel().getColumn(2).setResizable(false);
            tblHoaDonChiTiet2.getColumnModel().getColumn(3).setResizable(false);
            tblHoaDonChiTiet2.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel43.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel39.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel37.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel37, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Quản lý hóa đơn", jPanel18);

        jPanel2.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPhanTramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhanTramActionPerformed
        // TODO add your handling code here:
        applyDiscount();
    }//GEN-LAST:event_btnPhanTramActionPerformed

    private void btnTimKiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiem1ActionPerformed
        // TODO add your handling code here:
        String keyword = txtTimKiemMonAn.getText().trim();
        if (!keyword.matches("[a-zA-Zà-ỹÀ-Ỹ\\s]+")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên món ăn hợp lệ (chỉ chữ cái và khoảng trắng).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTimKiemMonAn.requestFocus();
            txtTimKiemMonAn.setText("");
            return;
        }
        MonAnDAO monAnDAO = new MonAnDAO();
        List<MonAn> list = monAnDAO.searchByName(keyword);
        DefaultTableModel model = (DefaultTableModel) tblMonAn.getModel();
        model.setRowCount(0);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        for (MonAn monAn : list) {
            Object[] row = new Object[]{
                monAn.getTenMonAn(),
                monAnDAO.getLoaiMonNameById(monAn.getMaLoaiMon()),
                monAn.getDvt(),
                currencyFormat.format(monAn.getDonGia())
            };
            model.addRow(row);
        }
    }//GEN-LAST:event_btnTimKiem1ActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblMonAnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMonAnMouseClicked
        // TODO add your handling code here:
        int row = tblMonAn.getSelectedRow();
        if (row >= 0) {
            String tenMonAn = (String) tblMonAn.getValueAt(row, 0);
            String donGiaStr = (String) tblMonAn.getValueAt(row, 3);
            double donGia;
            try {
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                Number number = currencyFormat.parse(donGiaStr);
                donGia = number.doubleValue();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Giá món ăn không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String quantityStr = JOptionPane.showInputDialog(null, "Nhập số lượng cho món ăn: " + tenMonAn, "Nhập số lượng", JOptionPane.PLAIN_MESSAGE);
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0) {
                        DefaultTableModel addModel = (DefaultTableModel) tblChiTietHoaDon1.getModel();
                        ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
                        boolean itemExists = false;
                        for (int i = 0; i < addModel.getRowCount(); i++) {
                            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                            String existingTenMonAn = (String) addModel.getValueAt(i, 0);
                            if (existingTenMonAn.equals(tenMonAn)) {
                                int currentQuantity = (int) addModel.getValueAt(i, 2);
                                int newQuantity = currentQuantity + quantity;
                                double totalPrice = donGia * newQuantity;
                                addModel.setValueAt(newQuantity, i, 2);
                                addModel.setValueAt(currencyFormat.format(totalPrice), i, 3);
                                ChiTietHoaDon chiTietHoaDon = chiTietHoaDonDAO.selectByTenMonAn(tenMonAn);
                                chiTietHoaDon.setSoLuong(newQuantity);
                                chiTietHoaDon.setMaHoaDon(null);  // Đảm bảo MaHoaDon luôn là null
                                chiTietHoaDonDAO.update(chiTietHoaDon);

                                itemExists = true;
                                break;
                            }
                        }
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        if (!itemExists) {
                            double totalPrice = donGia * quantity;
                            addModel.addRow(new Object[]{
                                tenMonAn,
                                currencyFormat.format(donGia),
                                quantity,
                                currencyFormat.format(totalPrice)
                            });
                            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(
                                    0,
                                    null,
                                    row,
                                    tenMonAn,
                                    quantity,
                                    donGia,
                                    false
                            );
                            chiTietHoaDonDAO.insert(chiTietHoaDon);
                        }
                        tblChiTietHoaDon1.revalidate();
                        tblChiTietHoaDon1.repaint();
                        JOptionPane.showMessageDialog(null, "Món ăn đã được thêm vào hóa đơn.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        loadChiTietHoaDonToTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một món ăn.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblMonAnMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblChiTietHoaDon1.getSelectedRow();
        if (selectedRow >= 0) {
            String tenMonAn = (String) tblChiTietHoaDon1.getValueAt(selectedRow, 0);
            DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon1.getModel();
            model.removeRow(selectedRow);
            ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
            ChiTietHoaDon chiTietHoaDon = chiTietHoaDonDAO.selectByTenMonAn(tenMonAn);
            if (chiTietHoaDon != null) {
                chiTietHoaDonDAO.delete(chiTietHoaDon.getIdCTHD());
                JOptionPane.showMessageDialog(null, "Đã xóa món ăn khỏi chi tiết hóa đơn.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                loadChiTietHoaDonToTable();
            } else {
                JOptionPane.showMessageDialog(null, "Món ăn không tồn tại trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn cần xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnTangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTangActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblChiTietHoaDon1.getSelectedRow();
        updateItemToFree(selectedRow);
    }//GEN-LAST:event_btnTangActionPerformed

    private void btnCapNhatSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatSoLuongActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblChiTietHoaDon1.getSelectedRow();
        updateQuantity(selectedRow);
    }//GEN-LAST:event_btnCapNhatSoLuongActionPerformed

    private void cboPhuongThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPhuongThucThanhToanActionPerformed
        // TODO add your handling code here:
        String selectedMethod = (String) cboPhuongThucThanhToan.getSelectedItem();
        if ("Thanh Toán Bằng Thẻ".equals(selectedMethod)) {
            txtKhachDua.setEnabled(false);
            txtTraKhach.setEnabled(false);
        } else {
            txtKhachDua.setEnabled(true);
            txtTraKhach.setEnabled(true);
        }
    }//GEN-LAST:event_cboPhuongThucThanhToanActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        try {
            String tongCongText = lblTongCong.getText().replaceAll("[^\\d]", "");
            double tongTien = Double.parseDouble(tongCongText);

            String khachDuaText = txtKhachDua.getText().replaceAll("[^\\d]", "");
            double khachDua = Double.parseDouble(khachDuaText);
            double tienTraKhach = khachDua - tongTien;

            if (tienTraKhach < 0) {
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa không đủ!");
            } else {
                NumberFormat vnCurrencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                String formattedTienTraKhach = vnCurrencyFormat.format(tienTraKhach);

                txtTraKhach.setText(formattedTienTraKhach);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date ngayLap = dateFormat.parse(lblNgay.getText());
                    java.sql.Date sqlDate = new java.sql.Date(ngayLap.getTime());
                    String maNV = null;
                    String maBanAn = null;
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaNV(maNV);
                    hoaDon.setMaBanAn(maBanAn);
                    hoaDon.setTongTien(tongTien);
                    hoaDon.setNgayLap(sqlDate);
                    HoaDonDAO hoaDonDAO = new HoaDonDAO();
                    hoaDonDAO.insert(hoaDon);
                    int maHoaDon = hoaDonDAO.findLatestHoaDon().getMaHoaDon();
                    ChiTietHoaDonDAO chiTietHoaDonDAO = new ChiTietHoaDonDAO();
                    List<ChiTietHoaDon> listChiTiet = chiTietHoaDonDAO.selectWithNullMaHoaDon();
                    for (ChiTietHoaDon chiTiet : listChiTiet) {
                        chiTiet.setMaHoaDon(maHoaDon);
                        chiTietHoaDonDAO.update(chiTiet);
                    }
                    JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                    loadChiTietHoaDonToTable();
                    loadHoaDonToTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi thanh toán: " + ex.getMessage());
                }

                // Sử dụng Timer để làm trống txtTraKhach sau 5 giây
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(() -> txtTraKhach.setText(""));
                    }
                }, 5000);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi định dạng số tiền: " + e.getMessage());
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnTimKiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiem2ActionPerformed
        // TODO add your handling code here:
        String keyword = txtTimKiem2.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hóa đơn để tìm kiếm.");
            txtTimKiem2.requestFocus();
            txtTimKiem2.setText("");
            return;
        }
        try {
            int maHoaDon = Integer.parseInt(keyword);
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            HoaDon hoaDon = hoaDonDAO.findById(maHoaDon);
            DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
            model.setRowCount(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (hoaDon != null) {
                model.addRow(new Object[]{
                    hoaDon.getMaHoaDon(),
                    hoaDon.getTongTien(),
                    dateFormat.format(hoaDon.getNgayLap())
                });
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn nào với mã: " + keyword);
                txtTimKiem2.requestFocus();
                txtTimKiem2.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã hóa đơn phải là số nguyên.");
            txtTimKiem2.requestFocus();
            txtTimKiem2.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTimKiem2ActionPerformed

    private void btnLamMoiHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiHoaDonActionPerformed
        // TODO add your handling code here:
        loadHoaDonToTable();
    }//GEN-LAST:event_btnLamMoiHoaDonActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            int maHoaDon = (int) tblHoaDon.getValueAt(selectedRow, 0);
            loadChiTietHoaDonByMaHoaDon(maHoaDon);
        }
        btnInLai.setEnabled(true);
        btnXuatExcel.setEnabled(true);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Excel File Location");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }
            String title = "Chi Tiết Hóa Đơn";
            ExcelExporter excelExporter = new ExcelExporter();
            excelExporter.exportToExcel(tblHoaDonChiTiet2, filePath, title);
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnInLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInLaiActionPerformed
        // TODO add your handling code here:
        InvoicePrinter.printDetailedInvoice(tblHoaDonChiTiet2);
    }//GEN-LAST:event_btnInLaiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HoaDonFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HoaDonFrm dialog = new HoaDonFrm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanel;
    private javax.swing.JButton btnCapNhatSoLuong;
    private javax.swing.JButton btnInLai;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiHoaDon;
    private javax.swing.JButton btnPhanTram;
    private javax.swing.JButton btnTang;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnTimKiem1;
    private javax.swing.JButton btnTimKiem2;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cboPhuongThucThanhToan;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblDatCoc;
    private javax.swing.JLabel lblNgay;
    private javax.swing.JLabel lblPhanTram;
    private javax.swing.JLabel lblTamTinh;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JPanel pnlMonAn;
    private javax.swing.JTable tblChiTietHoaDon1;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblHoaDonChiTiet2;
    private javax.swing.JTable tblMonAn;
    private javax.swing.JTabbedPane tblP1;
    private javax.swing.JTextField txtKhachDua;
    private javax.swing.JTextField txtTimKiem2;
    private javax.swing.JTextField txtTimKiemMonAn;
    private javax.swing.JTextField txtTraKhach;
    // End of variables declaration//GEN-END:variables
}
