package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;
import org.springframework.stereotype.Component;

import com.mycompany.doangiuakynt106.partials.BackgroundPanel;


public class SignUpUI extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SignUpUI.class.getName());

    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblSignUp;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton btnSignUp;
    private JButton btnExit;
    private JPanel mainPanel;

    public SignUpUI() {
        initComponents();
    }

    private void initComponents() {
        // ===== Khởi tạo các thành phần =====
        lblSignUp = new JLabel("ĐĂNG KÝ TÀI KHOẢN", SwingConstants.CENTER);
        lblEmail = new JLabel("Email:");
        lblPassword = new JLabel("Mật khẩu:");
        tfEmail = new JTextField();
        tfPassword = new JPasswordField();
        btnSignUp = new JButton("Tạo tài khoản");
        btnExit = new JButton("Quay lại");

        // ===== Cấu hình giao diện =====
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42)); // xanh đen kiểu cyber
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        lblSignUp.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblSignUp.setForeground(new Color(0, 204, 255));

        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmail.setForeground(Color.WHITE);

        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPassword.setForeground(Color.WHITE);

        tfEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tfPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tfEmail.setBackground(new Color(30, 41, 59));
        tfEmail.setForeground(Color.WHITE);
        tfEmail.setCaretColor(Color.WHITE);
        tfEmail.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));
        
        tfPassword.setBackground(new Color(30, 41, 59));
        tfPassword.setForeground(Color.WHITE);
        tfPassword.setCaretColor(Color.WHITE);
        tfPassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        // ===== Nút =====
        btnSignUp.setBackground(new Color(0, 204, 255));
        btnSignUp.setForeground(Color.BLACK);
        btnSignUp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        btnExit.setBackground(new Color(51, 65, 85));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // ===== Bố cục =====
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(lblSignUp)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(lblEmail)
                        .addComponent(lblPassword))
                    .addGap(18)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tfEmail, 250, 250, 300)
                        .addComponent(tfPassword, 250, 250, 300)))
                .addGap(10)
                .addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblSignUp)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(tfEmail, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(15)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(tfPassword, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(30)
                .addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        );

        // ===== Sự kiện =====
        btnSignUp.addActionListener(e -> onSignUp());
        btnExit.addActionListener(e -> onExit());
        
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
        setTitle("Đăng ký tài khoản");

        // ===== Toàn màn hình =====

        // 1. Không sử dụng setUndecorated để hiện thanh tiêu đề
        setUndecorated(false); 
        
        // 2. Tắt AlwaysOnTop để có thể swap tab ứng dụng khác
        setAlwaysOnTop(false); 
        
        // 3. Quy định kích thước cửa sổ thay vì tràn màn hình
        setSize(900, 650); 
        
        // 4. Cho phép thu nhỏ/phóng to tùy ý
        setResizable(true); 
        
        // 5. Đưa cửa sổ ra giữa màn hình
        setLocationRelativeTo(null);

        // Cho phép co giãn
        setResizable(true);
        
        // Hiển thị
        setVisible(true);
    }

    private void onSignUp() {
        if (SignUp.SignUpAdmin(tfEmail.getText(), new String(tfPassword.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                    "Đăng ký thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new SignInUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Đăng ký thất bại. Email có thể đã tồn tại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onExit() {
        new SignInUI().setVisible(true);
        dispose();
    }

    public static void displaySignUpUI() {
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

        EventQueue.invokeLater(() -> new SignUpUI().setVisible(true));
    }
}
