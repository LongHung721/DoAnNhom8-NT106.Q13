package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;
import org.springframework.stereotype.Component;

import com.mycompany.doangiuakynt106.partials.BackgroundPanel;

//@Component
public class ForgotPasswordUI extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ForgotPasswordUI.class.getName());

    private JLabel lblTitle;
    private JLabel lblEmail;
    private JLabel lblPrompt;
    private JTextField tfEmail;
    private JButton btnConfirm;
    private JButton btnBack;
    private JPanel mainPanel;

    public ForgotPasswordUI() {
        initComponents();
    }

    private void initComponents() {
        // ===== Khởi tạo =====
        lblTitle = new JLabel("QUÊN MẬT KHẨU", SwingConstants.CENTER);
        lblPrompt = new JLabel("Vui lòng nhập email để đặt lại mật khẩu", SwingConstants.CENTER);
        lblEmail = new JLabel("Email:");
        tfEmail = new JTextField();
        btnConfirm = new JButton("Xác nhận");
        btnBack = new JButton("Quay lại");

        // ===== Giao diện màu cyber =====
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42)); // xanh đen
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 204, 255));

        lblPrompt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPrompt.setForeground(Color.LIGHT_GRAY);

        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(Color.WHITE);

        tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfEmail.setBackground(new Color(30, 41, 59));
        tfEmail.setForeground(Color.WHITE);
        tfEmail.setCaretColor(Color.WHITE);
        tfEmail.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        // ===== Nút =====
        btnConfirm.setBackground(new Color(0, 204, 255));
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirm.setFocusPainted(false);
        btnConfirm.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        btnBack.setBackground(new Color(51, 65, 85));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // ===== Layout =====
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblTitle)
                .addComponent(lblPrompt)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblEmail)
                    .addGap(20)
                    .addComponent(tfEmail, 250, 250, 300))
                .addGap(15)
                .addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGap(15)
                .addComponent(lblPrompt)
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(tfEmail, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(30)
                .addComponent(btnConfirm, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        );

        // ===== Sự kiện =====
        btnBack.addActionListener(e -> {
            dispose();
            SignInUI.displaySignInUI();
        });

        btnConfirm.addActionListener(e -> {
            if (ForgotPassword.ForgotPasswordAdmin(tfEmail.getText())) {
                JOptionPane.showMessageDialog(this, "Email đã được gửi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gửi Email thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Background
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");
        mainPanel.setOpaque(true);
        mainPanel.setBackground(new Color(15, 23, 42, 180)); // màu xanh đen trong suốt
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(mainPanel, gbc);
        
        setContentPane(container);
        setTitle("Quên mật khẩu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Cho phép co giãn
        setResizable(true);

        // Hiển thị
        setVisible(true);
    }

    /*public static void main(String[] args) {
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
    }*/
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
