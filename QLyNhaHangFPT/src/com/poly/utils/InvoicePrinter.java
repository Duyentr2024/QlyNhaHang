package com.poly.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InvoicePrinter {

    public static void printDetailedInvoice(JTable table) {
        int width = 800; // Chiều rộng của hình ảnh
        int height = 600; // Chiều cao của hình ảnh

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        g.setFont(boldFont);
        g.setColor(Color.BLACK);
        g.drawString("Detailed Invoice", 50, 50);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        g.drawString("Date: " + currentDate, 50, 70);
        String[] columnNames = {"Mã hóa đơn", "Tên món ăn", "Đơn giá", "Số lượng", "Thành tiền"};
        int[] columnWidths = {100, 300, 80, 80, 80};
        int x = 50;
        int y = 100;
        for (int col = 0; col < columnNames.length; col++) {
            g.setFont(boldFont);
            g.drawString(columnNames[col], x, y);
            x += columnWidths[col];
        }
        y += 20;
        Font regularFont = new Font("Arial", Font.PLAIN, 14);
        g.setFont(regularFont);

        // Vẽ dữ liệu từ bảng
        double totalPrice = 0;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        for (int row = 0; row < table.getRowCount(); row++) {
            x = 50; // Reset vị trí x
            for (int col = 0; col < table.getColumnCount(); col++) {
                Object cellValue = table.getValueAt(row, col);
                String value = (cellValue != null) ? cellValue.toString() : "";
                g.drawString(value, x, y + row * 20);
                if (col == 4) { // Tính tổng giá tiền
                    try {
                        double price = (cellValue != null) ? NumberFormat.getNumberInstance(new Locale("vi", "VN")).parse(value).doubleValue() : 0;
                        Object quantityValue = table.getValueAt(row, 3);
                        totalPrice += price;
                    } catch (NumberFormatException | ParseException e) {
                        e.printStackTrace();
                    }
                }
                x += columnWidths[col];
            }
        }

        // In đậm và định dạng giá tiền thành VNĐ
        String totalPriceString = "Total: " + currencyFormat.format(totalPrice);
        g.setFont(boldFont); // Thiết lập font in đậm
        g.drawString(totalPriceString, 50, height - 50);

        g.dispose();

        // Save the image as .jpg file
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Invoice Image");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG file", "jpg");
            fileChooser.setFileFilter(filter);
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".jpg")) {
                    filePath += ".jpg";
                    fileToSave = new File(filePath);
                }
                ImageIO.write(image, "jpg", fileToSave);
                System.out.println("Detailed invoice saved to: " + fileToSave.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "In thành công !", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
