/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106client;

import com.mycompany.doangiuakynt106client.partials.BackgroundPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
/**
 *
 * @author ASUS
 */
public class HomeUI extends JFrame {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SignInUI.class.getName());

    private JLabel lblTitle;
    private JButton btnSignOut;
    private JPanel mainPanel;
    
    public HomeUI() {
        initComponents();
    }

    private void initComponents() {

        // ===== Components =====
        lblTitle = new JLabel("GIAO DIỆN CHÍNH", SwingConstants.CENTER);
        btnSignOut = new JButton("Đăng xuất");

        // ===== Main panel (mờ giống SignUp) =====
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42, 180));  // màu xanh đen mờ
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // ===== Label style =====
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 204, 255));

        // ===== Buttons =====
        btnSignOut.setBackground(new Color(0, 204, 255));
        btnSignOut.setForeground(Color.BLACK);
        btnSignOut.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSignOut.setFocusPainted(false);
        btnSignOut.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // ===== Layout =====
        /*GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitle)
                .addComponent(btnSignOut, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGap(30)
                .addComponent(btnSignOut, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18) 
        );*/
        
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            // btnSignOut căn phải
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSignOut, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                .addGap(22))
            // lblTitle căn trái, width cố định
            .addGroup(layout.createSequentialGroup()
                .addGap(44)
                .addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(24) // cách mép trên
                    .addComponent(btnSignOut, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(55) // khoảng cách giữa button và label
                    .addComponent(lblTitle)
                    .addContainerGap(171, Short.MAX_VALUE))
        );

        

        // ===== Events =====
        btnSignOut.addActionListener(e -> onSignOut());
        
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(mainPanel, gbc);
        
        setContentPane(container);
        
        setTitle("Trang chủ");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);  
    }
    
    private void onSignOut(){
        SignOut.SignOutUser();
        this.dispose();
        SignInUI.displaySignInUI();
    }
    
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new HomeUI().setVisible(true));
    }
    
    public static void displayHomeUI(){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Dark Metal".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new HomeUI().setVisible(true));
    }
}
