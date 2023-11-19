package com.sansang.BankManagement;

import javax.swing.*;
import java.sql.*;

public class myConnector {
    final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String DB_URL = "jdbc:mysql://localhost:3306/exambank";
    final static String USER = "root";
    final static String PASS = "123456";

    public ResultSet rs;
    public JLabel label = null;

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Connection successful");
            return con;
        }catch (Exception ex){
            ex.printStackTrace();

            return null;
        }
    }

    public void insertCustomer(String cusId, String fName, String lName, String street, String city, String branch, String phone){
        Connection con = getConnection();
        PreparedStatement pst;

        try {
            String query = "INSERT INTO customer (cust_id, firstname, lastname, street, ctiy, branch, phone)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

            pst = con.prepareStatement(query);

            pst.setString(1, cusId);
            pst.setString(2, fName);
            pst.setString(3, lName);
            pst.setString(4, street);
            pst.setString(5, city);
            pst.setString(6, branch);
            pst.setString(7, phone);

            if (pst.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Customer Add Successfully Inserted");
            }else {
                JOptionPane.showMessageDialog(null, "Customer Not Inserted");
            }

            con.close();
            pst.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public JLabel autoID(){
        Connection con = myConnector.getConnection();
        PreparedStatement pst;

        try {
            pst = con.prepareStatement("SELECT Max(cust_id) FROM Customer");
            rs = pst.executeQuery();
            rs.next();
            rs.getString("Max(cust_id)");

            if (rs.getString("Max(cust_id)") == null){
                label.setText("CS00001");
            }else {
                long csid = Long.parseLong(rs.getString("Max(cust_id)").substring(2, rs.getString("Max(cust_id)").length()));
                csid++;
                label.setText("CS" + String.format("%04d", csid));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return label;
    }

    public void insertAccount(String accId, String cusId, String cusName, String type, int balance){
        Connection con = getConnection();
        PreparedStatement pst;

        try {
            String query = "INSERT INTO account (acc_id, cust_id, customer, acc_type , balance)" +
                    " VALUES (?, ?, ?, ?, ?)";

            pst = con.prepareStatement(query);

            pst.setString(1, accId);
            pst.setString(2, cusId);
            pst.setString(3, cusName);
            pst.setString(4, type);
            pst.setInt(5, balance);

            if (pst.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Account Add Successfully Inserted");
            }else {
                JOptionPane.showMessageDialog(null, "Account Not Inserted");
            }

            con.close();
            pst.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void insertDeposit(String accId, String cusId, String date, int balance, int deposit){
        Connection con = getConnection();
        PreparedStatement pst;

        try {
            String query = "INSERT INTO deposit (acc_id, cust_id, date, balance , deposit)" +
                    " VALUES (?, ?, ?, ?, ?)";

            pst = con.prepareStatement(query);

            pst.setString(1, accId);
            pst.setString(2, cusId);
            pst.setString(3, date);
            pst.setInt(4, balance);
            pst.setInt(5, deposit);

            if (pst.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Deposit Add Successfully Inserted");
            }else {
                JOptionPane.showMessageDialog(null, "Deposit Not Inserted");
            }

            con.close();
            pst.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateAccountDeposit(String accId, int balance, int deposit){
        Connection con = getConnection();
        PreparedStatement pst;

        try {
            con.setAutoCommit(false);
            pst = con.prepareStatement("UPDATE account SET balance = balance + ? WHERE acc_id = ?");
            pst.setInt(1, deposit);
            pst.setString(2, accId);

            if (pst.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Account Deposit Successfully Updated");
                con.commit();
            }else {
                JOptionPane.showMessageDialog(null, "Account Not Updated");
            }

            con.close();
            pst.close();
        }catch (Exception ex){
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ex.printStackTrace();
        }
    }

    public void insertWithdraw(String accId, String cusId, String date, int balance, int withdraw){
        Connection con = getConnection();
        PreparedStatement pst;

        try {
            String query = "INSERT INTO withdraw (acc_id, cust_id, date, balance , withdraw)" +
                    " VALUES (?, ?, ?, ?, ?)";

            pst = con.prepareStatement(query);

            pst.setString(1, accId);
            pst.setString(2, cusId);
            pst.setString(3, date);
            pst.setInt(4, balance);
            pst.setInt(5, withdraw);

            if (pst.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Withdraw Add Successfully Inserted");
            }else {
                JOptionPane.showMessageDialog(null, "Withdraw Not Inserted");
            }

            con.close();
            pst.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateAccountWithdraw(String accId, int balance, int withdraw){
        Connection con = getConnection();
        PreparedStatement pst;

        try {
            con.setAutoCommit(false);
            pst = con.prepareStatement("UPDATE account SET balance = balance - ? WHERE acc_id = ?");
            pst.setInt(1, withdraw);
            pst.setString(2, accId);

            if (pst.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Account Withdraw Successfully Updated");
                con.commit();
            }else {
                JOptionPane.showMessageDialog(null, "Account Not Updated");
            }

            con.close();
            pst.close();
        }catch (Exception ex){
            try {
                con.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ex.printStackTrace();
        }
    }

}
