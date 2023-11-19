package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BalanceCheck extends JFrame{

    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public BalanceCheck(){

        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         Balance Check");
        setContentPane(balanceCheckPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750,450);
        setLocationRelativeTo(null);

        date();
        clearBalance();
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
                clearBalance();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void clearBalance(){
        txtAccountNo.setText("AC");
        lblCustNo.setText("CS0000");
        lblFname.setText("First Name");
        lblLname.setText("Last Name");
        lblBalance.setText("0");

        txtAccountNo.requestFocus();
    }

    public void find(){
        String accNo = txtAccountNo.getText();
        con = myConnector.getConnection();

        try {
            ps = con.prepareStatement("SELECT c.cust_id, c.firstname, c.lastname, a.balance FROM customer c, account a  " +
                    "WHERE c.cust_id  = a.cust_id AND a.acc_id = ?");
            ps.setString(1, accNo );
            rs = ps.executeQuery();

            if (!rs.next()){
                JOptionPane.showMessageDialog(null, "Account No not Found");
            }else {
                String cId = rs.getString(1);
                String fName = rs.getString(2);
                String lName = rs.getString(3);
                String balan = rs.getString(4);
                lblCustNo.setText(cId);
                lblFname.setText(fName.trim());
                lblLname.setText(lName.trim());
                lblBalance.setText(balan);
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

    private JPanel balanceCheckPanel;
    private JPanel balance;
    private JTextField txtAccountNo;
    private JButton btnFind;
    private JLabel lblDate;
    private JLabel lblCustNo;
    private JLabel lblFname;
    private JLabel lblLname;
    private JLabel lblBalance;
    private JButton btnClear;
    private JButton btnCancel;
}
