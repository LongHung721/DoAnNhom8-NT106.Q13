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
public class ForgotPasswordUI extends JFrame {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SignInUI.class.getName());

    private JLabel lblIdcard;
    private JLabel lblNewPassword;
    private JLabel lblTitle;
    private JTextField tfIdcard;
    private JPasswordField tfNewPassword;
    private JButton btnConfirm;
    private JButton btnExit;
    private JPanel mainPanel;

    public ForgotPasswordUI() {
        initComponents();
    }

    private void initComponents() {

        // ===== Components =====
        lblTitle = new JLabel("ĐẶT LẠI MẬT KHẨU", SwingConstants.CENTER);
        lblIdcard = new JLabel("Số CCCD:");
        lblNewPassword = new JLabel("Mật khẩu mới:");

        tfIdcard = new JTextField();
        tfNewPassword = new JPasswordField();

        btnConfirm = new JButton("Xác nhận");
        btnExit = new JButton("Quay lại");

        // ===== Main panel (mờ giống SignUp) =====
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42, 180));  // màu xanh đen mờ
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // ===== Label style =====
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 204, 255));

        lblIdcard.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblIdcard.setForeground(Color.WHITE);

        lblNewPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblNewPassword.setForeground(Color.WHITE);

        // ===== TextField style =====
        tfIdcard.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfNewPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tfIdcard.setBackground(new Color(30, 41, 59));
        tfIdcard.setForeground(Color.WHITE);
        tfIdcard.setCaretColor(Color.WHITE);
        tfIdcard.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        tfNewPassword.setBackground(new Color(30, 41, 59));
        tfNewPassword.setForeground(Color.WHITE);
        tfNewPassword.setCaretColor(Color.WHITE);
        tfNewPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        // ===== Buttons =====
        btnConfirm.setBackground(new Color(0, 204, 255));
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        btnExit.setBackground(new Color(51, 65, 85));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

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
                        .addComponent(lblIdcard)
                        .addComponent(lblNewPassword))
                    .addGap(16)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tfIdcard, 300, 350, 400)
                        .addComponent(tfNewPassword, 300, 350, 400)))
                .addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdcard)
                    .addComponent(tfIdcard, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNewPassword)
                    .addComponent(tfNewPassword, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGap(35)
                .addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
        );

        // ===== Events =====
        btnConfirm.addActionListener(e -> onResetPassword());
        btnExit.addActionListener(e -> onExit());
        
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(mainPanel, gbc);
        
        setContentPane(container);
        
        setTitle("Quên mật khẩu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);  
    }

    private void onResetPassword() {
        if (ForgotPassword.ForgotPasswordUser(tfIdcard.getText(), new String(tfNewPassword.getPassword()))) {
            JOptionPane.showMessageDialog(
                    null,
                    "Đặt lại mật khẩu thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(this,
                    "Đặt lại mật khẩu thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onExit() {
        dispose();
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

        EventQueue.invokeLater(() -> new ForgotPasswordUI().setVisible(true));
    }
    
    public static void displayForgotPasswordUI(){
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

        EventQueue.invokeLater(() -> new ForgotPasswordUI().setVisible(true));
    }
}
