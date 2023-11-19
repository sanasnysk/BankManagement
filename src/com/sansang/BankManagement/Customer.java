package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Customer extends JFrame{
/* customer 고객 손님 */
    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    public Customer(){

        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         New Customer");
        setContentPane(customerPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750,650);
        setLocationRelativeTo(null);

        //setVisible(true);
        autoID();
        branch();
        buttonActionListener();
        clearCustomer();
    }

    public void buttonActionListener(){
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCustomer();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void saveCustomer(){
        String cuId = lblCustomerId.getText();
        String fName = txtfName.getText();
        String lName = txtlName.getText();
        String street = txtStreet.getText();
        String city = txtCity.getText();
        String branch = (String) cbBranch.getSelectedItem();
        String phone = txtPhone.getText();

        if (fName.isEmpty()){
            JOptionPane.showMessageDialog(null, "First Name not Data");
        } else if (lName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Last Name not Data");
        } else if (street.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Street not Data");
        } else if (city.isEmpty()) {
            JOptionPane.showMessageDialog(null, "City not Data");
        } else if (branch.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Branch not Data");
        } else if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone not Data");
        }else {
            mc.insertCustomer(cuId,fName,lName,street,city,branch,phone);

            autoID();
            clearCustomer();
        }
    }

    public void clearCustomer(){
        txtfName.setText("");
        txtlName.setText("");
        txtStreet.setText("");
        txtCity.setText("");
        cbBranch.setSelectedIndex(-1);
        txtPhone.setText("");

        txtfName.requestFocus();
    }

    public void branch(){
        con = myConnector.getConnection();
        try {
            ps = con.prepareStatement("SELECT * FROM branch");
            ResultSet rs = ps.executeQuery();
            cbBranch.removeAllItems();

            while (rs.next()){
                cbBranch.addItem(rs.getString(2));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void autoID(){
        Connection con = myConnector.getConnection();
        PreparedStatement pst;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT Max(cust_id) FROM Customer");
            rs.next();
            rs.getString("Max(cust_id)");

            if (rs.getString("Max(cust_id)") == null){
                lblCustomerId.setText("CS00001");
            }else {
                long id = Long.parseLong(rs.getString("Max(cust_id)").substring(2, rs.getString("Max(cust_id)").length()));
                id++;
                lblCustomerId.setText("CS" + String.format("%05d", id));

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private JPanel customerPanel;
    private JPanel customer;
    private JTextField txtfName;
    private JTextField txtlName;
    private JTextField txtStreet;
    private JTextField txtCity;
    private JTextField txtPhone;
    private JButton btnAdd;
    private JButton btnCancel;
    private JLabel lblCustomerId;
    private JComboBox cbBranch;
}
