/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.utils;

/**
 *
 * @author duyen
 */
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    public void exportToExcel(JTable table, String filePath, String title) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Chi Tiết Hóa Đơn");
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(title);
            CellStyle titleCellStyle = workbook.createCellStyle();
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 22);
            titleRow.setHeightInPoints(29);
            titleCellStyle.setFont(titleFont);
            titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleCell.setCellStyle(titleCellStyle);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, table.getColumnCount() - 1));
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Row headerRow = sheet.createRow(1);
            for (int i = 0; i < table.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(table.getColumnName(i));
                cell.setCellStyle(headerCellStyle);
                sheet.setColumnWidth(i, 20 * 256);
            }
            headerRow.setHeightInPoints(25);
            for (int row = 0; row < table.getRowCount(); row++) {
                Row dataRow = sheet.createRow(row + 2);
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Cell cell = dataRow.createCell(col);
                    cell.setCellValue(table.getValueAt(row, col).toString());
                    CellStyle dataCellStyle = workbook.createCellStyle();
                    dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
                    dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    cell.setCellStyle(dataCellStyle);
                }
                dataRow.setHeightInPoints(25);
            }
            for (int r = 0; r < sheet.getLastRowNum() + 1; r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    for (int c = 0; c < table.getColumnCount(); c++) {
                        Cell cell = row.getCell(c);
                        if (cell != null) {
                            CellStyle borderCellStyle = workbook.createCellStyle();
                            borderCellStyle.cloneStyleFrom(cell.getCellStyle());
                            borderCellStyle.setBorderBottom(BorderStyle.THIN);
                            borderCellStyle.setBorderTop(BorderStyle.THIN);
                            borderCellStyle.setBorderRight(BorderStyle.THIN);
                            borderCellStyle.setBorderLeft(BorderStyle.THIN);
                            cell.setCellStyle(borderCellStyle);
                        }
                    }
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                JOptionPane.showMessageDialog(null, "Xuất chi tiết hóa đơn thành công !");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error exporting to Excel: " + ex.getMessage());
        }
    }
}
