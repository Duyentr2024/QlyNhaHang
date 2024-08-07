/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.poly.DAO;

import com.poly.entity.BanAn;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.List;

public class MyForm extends javax.swing.JFrame {

    private JPanel pnlTenBan;
    private BanAnDAO banAnDAO = new BanAnDAO();

    public MyForm() {
        initComponents();
        fillTenBanAn(); // Fill panel with table names
    }

    private void initComponents() {
        // Initialize your panel and other components here
        pnlTenBan = new JPanel();
        pnlTenBan.setLayout(new GridLayout(0, 1)); // Set the layout to display items vertically
        // Add panel to your form layout
        this.add(pnlTenBan);
        // Other UI components initialization
    }

    private void fillTenBanAn() {
        pnlTenBan.removeAll(); // Clear the panel
        List<BanAn> list = banAnDAO.selectAll(); // Retrieve all tables
        for (BanAn banAn : list) {
            JLabel lblTenBan = new JLabel(banAn.getTenBan()); // Create a label for each table name
            pnlTenBan.add(lblTenBan); // Add label to the panel
        }
        pnlTenBan.revalidate(); // Refresh the panel to display new components
        pnlTenBan.repaint();    // Repaint the panel to ensure proper display
    }

    // Main method or other methods for the form
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyForm().setVisible(true);
            }
        });
    }
}
