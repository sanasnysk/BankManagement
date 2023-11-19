package com.sansang.BankManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Report extends JFrame{

    Connection con;
    Statement stmt = null;
    ResultSet rs = null;
    DefaultTableModel model;
    public Report(){

        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         Report Search");
        setContentPane(reportPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1250,650);
        setLocationRelativeTo(null);

        showRecordTable();
        textAddKeyListener();
        buttonActionListener();
    }

    public void textAddKeyListener() {
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                DefaultTableModel dtm = new DefaultTableModel();
                dtm.addColumn("customer No");
                dtm.addColumn("First Name");
                dtm.addColumn("Last Name");
                dtm.addColumn("Street");
                dtm.addColumn("City");
                dtm.addColumn("branch");
                dtm.addColumn("Phone");
                dtm.addColumn("Balance");

                tblReport.setModel(dtm);
                searchStudentTable(tblReport, txtSearch.getText());
                showRecordTable();

            }
        });
    }

    public void showRecordTable() {
        String search = txtSearch.getText();

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("customer No");
        dtm.addColumn("First Name");
        dtm.addColumn("Last Name");
        dtm.addColumn("Street");
        dtm.addColumn("City");
        dtm.addColumn("branch");
        dtm.addColumn("Phone");
        dtm.addColumn("Balance");

        tblReport.setModel(dtm);
        searchStudentTable(tblReport, search);
        model = (DefaultTableModel) tblReport.getModel();

        JTableHeader tableHeader = tblReport.getTableHeader();
        Font tableFont = new Font("Sanserif", Font.BOLD, 18);
        tableHeader.setFont(tableFont);
        tableHeader.setBackground(new Color(25, 200, 255));
        tableHeader.setForeground(Color.white);

        tblReport.setRowHeight(30);
        tblReport.setShowGrid(true);
        tblReport.setGridColor(Color.BLUE);
        tblReport.setSelectionBackground(new Color(255, 197, 242));
        tblReport.setFocusTraversalKeysEnabled(false);
        tblReport.setFont(new Font("Sanserif", Font.BOLD, 16));
        tblReport.setAlignmentX(0);

        TableColumnModel columns = tblReport.getColumnModel();
        columns.getColumn(0).setPreferredWidth(20);
        columns.getColumn(1).setMinWidth(50);
        columns.getColumn(2).setMinWidth(50);
        columns.getColumn(3).setPreferredWidth(30);
        columns.getColumn(4).setPreferredWidth(30);
        columns.getColumn(5).setMinWidth(100);
        columns.getColumn(6).setPreferredWidth(20);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) JTable.CENTER_ALIGNMENT);

        columns.getColumn(0).setCellRenderer(centerRenderer);
        columns.getColumn(3).setCellRenderer(centerRenderer);
        columns.getColumn(4).setCellRenderer(centerRenderer);
        columns.getColumn(6).setCellRenderer(centerRenderer);

    }

    public void searchStudentTable(JTable table, String valueToSearch) {
        // Table Search
        try {
            con = myConnector.getConnection();
            stmt = con.createStatement();
            String query = "SELECT c.cust_id, c.firstname, c.lastname, c.street, c.ctiy, c.branch, c.phone, a.balance FROM customer c" +
                    " JOIN account a ON c.cust_id = a.cust_id " +
                    " WHERE CONCAT(c.cust_id,firstname,lastname,street,ctiy,branch,phone) LIKE '%" + valueToSearch + "%'";

            rs = stmt.executeQuery(query);

            model = (DefaultTableModel) table.getModel();

            Object[] row;
            while (rs.next()) {
                row = new Object[8];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);

                model.addRow(row);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void buttonActionListener(){
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private JPanel reportPanel;
    private JPanel report;
    private JTextField txtSearch;
    private JTable tblReport;
    private JButton btnBack;
}
