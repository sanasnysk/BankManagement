package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Account extends JFrame{
/* account 계정 계좌 */
    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public Account(){

        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         New Account");
        setContentPane(accountPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750,450);
        setLocationRelativeTo(null);

        autoID();
        buttonActionListener();
        clearAccount();

    }

    public void buttonActionListener(){
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAccount();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }

    public void saveAccount(){
        String acId = lblAccountId.getText();
        String cuId = txtCustomerID.getText();
        String cfName = txtCustomer.getText();
        String type = cbAccount.getSelectedItem().toString();
        String balance = txtBalance.getText();

        if (cuId.isEmpty()){
            JOptionPane.showMessageDialog(null, "Customer ID not Data");
        } else if (cfName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer not Data");
        } else if (type.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account Type not Data");
        } else if (balance.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Account Balance not Data");
        } else {
            mc.insertAccount(acId,cuId,cfName,type, Integer.parseInt(balance));

            autoID();
            clearAccount();
        }
    }

    public void clearAccount(){
        txtCustomerID.setText("CS00000");
        txtCustomer.setText("Name");
        txtBalance.setText("0");
        cbAccount.setSelectedIndex(-1);

        txtCustomerID.requestFocus();
    }

    public void autoID(){
        Connection con = myConnector.getConnection();
        PreparedStatement pst;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT Max(acc_id) FROM account");
            rs.next();
            rs.getString("Max(acc_id)");

            if (rs.getString("Max(acc_id)") == null){
                lblAccountId.setText("AC00001");
            }else {
                long id = Long.parseLong(rs.getString("Max(acc_id)").substring(2, rs.getString("Max(acc_id)").length()));
                id++;
                lblAccountId.setText("AC" + String.format("%05d", id));

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void find(){
        String custId = txtCustomerID.getText();
        con = myConnector.getConnection();

        try {
            ps = con.prepareStatement("SELECT * FROM customer WHERE cust_id = ?");
            ps.setString(1, custId);
            rs = ps.executeQuery();

            if (!rs.next()){
                JOptionPane.showMessageDialog(null, "Customer No not Found");
            }else {
                String fName = rs.getString("firstname");
                txtCustomer.setText(fName.trim());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private JPanel account;
    private JTextField txtCustomerID;
    private JTextField txtCustomer;
    private JTextField txtBalance;
    private JButton btnAdd;
    private JLabel lblAccountId;
    private JComboBox cbAccount;
    private JPanel accountPanel;
    private JButton btnCancel;
    private JButton btnFind;
}
