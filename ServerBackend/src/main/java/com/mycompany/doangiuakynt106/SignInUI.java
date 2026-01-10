package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;
import org.springframework.stereotype.Component;

import com.mycompany.doangiuakynt106.partials.BackgroundPanel;

public class SignInUI extends JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(SignInUI.class.getName());

    private JLabel lblEmail;
    private JLabel lblPassword;
    private JLabel lblTitle;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton btnSignIn;
    private JButton btnForgotPassword;
    private JButton btnSignUp;
    private JPanel mainPanel;

    public SignInUI() {
        initComponents();
    }

    private void initComponents() {

        // ===== Components =====
        lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        lblEmail = new JLabel("Email:");
        lblPassword = new JLabel("Mật khẩu:");

        tfEmail = new JTextField();
        tfPassword = new JPasswordField();

        btnSignIn = new JButton("Đăng nhập");
        btnForgotPassword = new JButton("Quên mật khẩu");
        btnSignUp = new JButton("Đăng ký");

        // ===== Main panel (mờ giống SignUp) =====
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42, 180));  // màu xanh đen mờ
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // ===== Label style =====
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 204, 255));

        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblEmail.setForeground(Color.WHITE);

        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblPassword.setForeground(Color.WHITE);

        // ===== TextField style =====
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

        btnSignUp.setBackground(new Color(51, 65, 85));
        btnSignUp.setForeground(Color.WHITE);
        btnSignUp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSignUp.setFocusPainted(false);
        btnSignUp.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

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
                        .addComponent(lblEmail)
                        .addComponent(lblPassword))
                    .addGap(16)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tfEmail, 300, 350, 400)
                        .addComponent(tfPassword, 300, 350, 400)))
                .addComponent(btnSignIn, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnForgotPassword, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGap(30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(tfEmail, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGap(18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(tfPassword, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGap(35)
                .addComponent(btnSignIn, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(btnForgotPassword, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
        );

        // ===== Events =====
        btnSignIn.addActionListener(e -> onSignIn());
        btnForgotPassword.addActionListener(e -> onForgotPassword());
        btnSignUp.addActionListener(e -> onSignUp());

        // ===== Background image =====
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(mainPanel, gbc);

        setContentPane(container);

        // ===== Window settings =====
        setTitle("Đăng nhập");
        
        
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

        setVisible(true);
    }

    private void onSignIn() {
        if (SignIn.SignInAdmin(tfEmail.getText(), new String(tfPassword.getPassword()))) {
            dispose();
            new HomeUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onForgotPassword() {
        new ForgotPasswordUI().setVisible(true);
        dispose();
    }

    private void onSignUp() {
        new SignUpUI().setVisible(true);
        dispose();
    }

    public static void displaySignInUI() {
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
}
