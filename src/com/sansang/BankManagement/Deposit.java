package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deposit extends JFrame{
/* deposit 예금 입금 */
    myConnector mc = new myConnector();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public Deposit(){
        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                         Deposit");
        setContentPane(depositPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550,450);
        setLocationRelativeTo(null);

        date();
        buttonActionListener();
        clearDeposit();

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
                saveDeposit();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void saveDeposit(){
        String accNo = txtAccountNo.getText();
        String custNo = lblCustNo.getText();
        String fName = lblFname.getText();
        String lName = lblLname.getText();
        String date = lblDate.getText();
        String amount = txtDeposit.getText();
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
            mc.insertDeposit(accNo,custNo,date, Integer.parseInt(balance), Integer.parseInt(amount));

            mc.updateAccountDeposit(accNo, Integer.parseInt(balance), Integer.parseInt(amount));

            clearDeposit();
        }
    }

    public void clearDeposit(){
        txtAccountNo.setText("AC");
        txtDeposit.setText("0");
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

    private JPanel depositPanel;
    private JPanel deposit;
    private JTextField txtAccountNo;
    private JLabel lblCustNo;
    private JLabel lblFname;
    private JLabel lblLname;
    private JLabel lblDate;
    private JTextField txtDeposit;
    private JButton btnOk;
    private JButton btnFind;
    private JButton btnCancel;
    private JLabel lblBanlance;
}
