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
public class SignInUI extends JFrame {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SignInUI.class.getName());

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblTitle;
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnSignIn;
    private JButton btnForgotPassword;
    private JPanel mainPanel;

    public SignInUI() {
        initComponents();
    }

    private void initComponents() {

        // ===== Components =====
        lblTitle = new JLabel("ĐĂNG NHẬP KHÁCH", SwingConstants.CENTER);
        lblUsername = new JLabel("Tên tài khoản:");
        lblPassword = new JLabel("Mật khẩu:");

        tfUsername = new JTextField();
        tfPassword = new JPasswordField();

        btnSignIn = new JButton("Đăng nhập");
        btnForgotPassword = new JButton("Quên mật khẩu");

        // ===== Main panel (mờ giống SignUp) =====
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42, 180));  // màu xanh đen mờ
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // ===== Label style =====
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 204, 255));

        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblUsername.setForeground(Color.WHITE);

        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPassword.setForeground(Color.WHITE);

        // ===== TextField style =====
        tfUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tfUsername.setBackground(new Color(30, 41, 59));
        tfUsername.setForeground(Color.WHITE);
        tfUsername.setCaretColor(Color.WHITE);
        tfUsername.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        tfPassword.setBackground(new Color(30, 41, 59));
        tfPassword.setForeground(Color.WHITE);
        tfPassword.setCaretColor(Color.WHITE);
        tfPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        // ===== Buttons =====
        btnSignIn.setBackground(new Color(0, 204, 255));
        btnSignIn.setForeground(Color.BLACK);
        btnSignIn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSignIn.setFocusPainted(false);
        btnSignIn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        btnForgotPassword.setBackground(new Color(51, 65, 85));
        btnForgotPassword.setForeground(Color.WHITE);
        btnForgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnForgotPassword.setFocusPainted(false);
        btnForgotPassword.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // ===== Layout =====
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitle)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblUsername)
                        .addComponent(lblPassword))
                    .addGap(16)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tfUsername, 300, 350, 400)
                        .addComponent(tfPassword, 300, 350, 400)))
                .addComponent(btnSignIn, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnForgotPassword, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(tfUsername, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(tfPassword, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGap(35)
                .addComponent(btnSignIn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(btnForgotPassword, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
        );

        // ===== Events =====
        btnSignIn.addActionListener(e -> onSignIn());
        btnForgotPassword.addActionListener(e -> onForgotPassword());
        
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(mainPanel, gbc);
        
         setContentPane(container);
        
        setTitle("Đăng nhập");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);  
    }

    private void onSignIn() {
        if (SignIn.SignInUser(tfUsername.getText(), new String(tfPassword.getPassword()))) {
            dispose();
            HomeUI.displayHomeUI();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onForgotPassword() {
        dispose();
        ForgotPasswordUI.displayForgotPasswordUI();
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

        EventQueue.invokeLater(() -> new SignInUI().setVisible(true));
    }
    
    public static void displaySignInUI(){
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

        EventQueue.invokeLater(() -> new SignInUI().setVisible(true));
    }
}
