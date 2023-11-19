package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserAccount extends JFrame{

    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public UserAccount(){
        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         Balance Check");
        setContentPane(userAccountPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750,450);
        setLocationRelativeTo(null);

        date();
        clearUser();
        buttonActionListener();
    }

    public void buttonActionListener(){
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void clearUser(){
        txtCustomerNo.setText("CS");
        lblAccountNo.setText("AC00000");
        lblFname.setText("First Name");
        lblLname.setText("Last Name");
        lblBalance.setText("0");

        txtCustomerNo.requestFocus();
    }

    public void find(){
        String accNo = txtCustomerNo.getText();
        con = myConnector.getConnection();

        try {
            ps = con.prepareStatement("SELECT a.acc_id, c.firstname, c.lastname, a.balance FROM customer c, account a  " +
                    "WHERE c.cust_id  = a.cust_id AND c.cust_id = ?");
            ps.setString(1, accNo );
            rs = ps.executeQuery();

            if (!rs.next()){
                JOptionPane.showMessageDialog(null, "Customer No not Found");
            }else {
                String acId = rs.getString(1);
                String fName = rs.getString(2);
                String lName = rs.getString(3);
                String balan = rs.getString(4);
                lblAccountNo.setText(acId.trim());
                lblFname.setText(fName.trim());
                lblLname.setText(lName.trim());
                lblBalance.setText(balan.trim());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void date(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        lblDate.setText(date);
    }

    private JPanel userAccountPanel;
    private JPanel balance;
    private JTextField txtCustomerNo;
    private JButton btnFind;
    private JLabel lblDate;
    private JLabel lblBalance;
    private JLabel lblFname;
    private JLabel lblLname;
    private JLabel lblAccountNo;
    private JButton btnClear;
    private JButton btnCancel;
}
