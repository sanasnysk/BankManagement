package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transfer extends JFrame{
    /* transfer 옮기다 이전 이송 이양  */
    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    PreparedStatement ps1;
    PreparedStatement ps2;
    PreparedStatement ps3;
    ResultSet rs;

    public Transfer(){
        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         Transfer");
        setContentPane(transferPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550,450);
        setLocationRelativeTo(null);

        buttonActionListener();
        clearTransfer();

    }

    public void buttonActionListener(){
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });

        btnTransfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTransfer();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void saveTransfer(){
        String frAcc = txtFromAccount.getText();
        String balance = txtBalance.getText();
        String toAcc = txtToAccount.getText();
        String amount = txtAmount.getText();

        if (frAcc.isEmpty()){
            JOptionPane.showMessageDialog(null, "Customer ID not Data");
        } else if (balance.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer not Data");
        } else if (toAcc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account Type not Data");
        } else if (amount.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Account Balance not Data");
        } else {
            try {
                con = myConnector.getConnection();
                con.setAutoCommit(false);

                int result = JOptionPane.showConfirmDialog(null,
                        "Would you like to transfer to the next account?? \n" +
                                "To Account : " + toAcc + "", "Transfer", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION){
                    // sender
                    ps1 = con.prepareStatement("UPDATE account SET balance = balance - ? WHERE acc_id = ?");
                    ps1.setString(1,amount);
                    ps1.setString(2,frAcc);
                    ps1.executeUpdate();

                    // receiver
                    ps2 = con.prepareStatement("UPDATE account SET balance = balance + ? WHERE acc_id = ?");
                    ps2.setString(1,amount);
                    ps2.setString(2,toAcc);
                    ps2.executeUpdate();

                    // insert
                    String query = "INSERT INTO transfer (f_account, to_account, amount) VALUES (?, ?, ?)";
                    ps3 = con.prepareStatement(query);
                    ps3.setString(1, frAcc);
                    ps3.setString(2, toAcc);
                    ps3.setString(3, amount);

                    if (ps3.executeUpdate() > 0){
                        JOptionPane.showMessageDialog(null, "Transfer Add Successfully");
                        con.commit();

                        clearTransfer();
                    }else {
                        JOptionPane.showMessageDialog(null, "Transfer No Inserted");
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "Transfer canceled it");
                }

            }catch (Exception ex){
                ex.printStackTrace();
                try {
                    con.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void clearTransfer(){
        txtFromAccount.setText("AC");
        txtBalance.setText("0");
        txtToAccount.setText("AC");
        txtAmount.setText("0");

        txtFromAccount.requestFocus();
    }

    public void find(){
        String procod = txtFromAccount.getText();
        con = myConnector.getConnection();

        try {
            String query = "SELECT c.cust_id, c.firstname, c.lastname, a.balance FROM customer c, account a  " +
                    "WHERE c.cust_id  = a.cust_id AND a.acc_id = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, procod);
            rs = ps.executeQuery();

            if (!rs.next()){
                JOptionPane.showMessageDialog(null, "Account ID No not Found");
            }else {
                String balan = rs.getString(4);
                txtBalance.setText(balan.trim());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private JPanel transferPanel;
    private JPanel transfer;
    private JTextField txtFromAccount;
    private JButton btnFind;
    private JTextField txtBalance;
    private JTextField txtToAccount;
    private JTextField txtAmount;
    private JButton btnCancel;
    private JButton btnTransfer;
}
