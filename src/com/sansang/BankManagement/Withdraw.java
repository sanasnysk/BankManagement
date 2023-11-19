package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Withdraw extends JFrame{
    /* withdraw 꺼내다 출금 인출 */
    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Withdraw(){
        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         Withdraw");
        setContentPane(withdrawPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550,450);
        setLocationRelativeTo(null);

        date();
        buttonActionListener();
        clearWithdraw();
    }

    public void buttonActionListener(){
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWithdranw();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void saveWithdranw(){
        String accNo = txtAccountNo.getText();
        String custNo = lblCustNo.getText();
        String fName = lblFname.getText();
        String lName = lblLname.getText();
        String date = lblDate.getText();
        String amount = txtWithdraw.getText();
        String balance = lblBanlance.getText();

        if (accNo.isEmpty()){
            JOptionPane.showMessageDialog(null, "Customer ID not Data");
        } else if (custNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Customer not Data");
        } else if (date.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Account Type not Data");
        } else if (balance.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Account Balance not Data");
        }else if (amount.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "Account Balance not Data");
        } else {
            mc.insertWithdraw(accNo,custNo,date, Integer.parseInt(balance), Integer.parseInt(amount));

            mc.updateAccountWithdraw(accNo, Integer.parseInt(balance), Integer.parseInt(amount));

            clearWithdraw();
        }
    }

    public void clearWithdraw(){
        txtAccountNo.setText("AC");
        txtWithdraw.setText("0");
        lblCustNo.setText("CS00000");
        lblFname.setText("First Name");
        lblLname.setText("Last Name");
        lblBanlance.setText("0");

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
                lblBanlance.setText(balan);
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

    private JPanel withdrawPanel;
    private JPanel withdraw;
    private JTextField txtAccountNo;
    private JButton btnFind;
    private JLabel lblDate;
    private JLabel lblCustNo;
    private JLabel lblFname;
    private JLabel lblLname;
    private JLabel lblBanlance;
    private JTextField txtWithdraw;
    private JButton btnOk;
    private JButton btnCancel;
}
