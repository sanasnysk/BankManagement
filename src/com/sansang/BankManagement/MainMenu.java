package com.sansang.BankManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame{
    public MainMenu() {

        setContentPane(mainPanel);
        setIconImage(new ImageIcon("src/image/bank.png").getImage());
        setTitle("                   Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        menuItemActionListener();
    }

    public void menuItemActionListener(){
        customerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             Customer cust = new Customer();
             cust.setVisible(true);
            }
        });

        accountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account ac = new Account();
                ac.setVisible(true);
            }
        });

        depositItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit dp = new Deposit();
                dp.setVisible(true);
            }
        });

        withdrawItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Withdraw wd = new Withdraw();
                wd.setVisible(true);
            }
        });

        accountToAccountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transfer tf = new Transfer();
                tf.setVisible(true);
            }
        });

        balanceCheckItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BalanceCheck bc = new BalanceCheck();
                bc.setVisible(true);
            }
        });

        userAccountItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserAccount ua = new UserAccount();
                ua.setVisible(true);
            }
        });

        customerReportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Report rp = new Report();
                rp.setVisible(true);
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    private JPanel mainPanel;
    private JMenuItem customerItem;
    private JMenuBar menubar;
    private JMenu fileMenu;
    private JMenuItem accountItem;
    private JMenu transactionMenu;
    private JMenuItem depositItem;
    private JMenuItem withdrawItem;
    private JMenu transferMenu;
    private JMenuItem accountToAccountItem;
    private JMenu reportMenu;
    private JMenuItem customerReportItem;
    private JMenu balanceMenu;
    private JMenuItem balanceCheckItem;
    private JMenu accountMenu;
    private JMenuItem userAccountItem;
    private JMenu exitMenu;
    private JMenuItem exitItem;
    private JPanel pnlImage;
}
