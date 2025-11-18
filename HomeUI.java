package com.mycompany.doangiuakynt106;

import java.awt.*;
import javax.swing.*;
import org.springframework.stereotype.Component;
import com.mycompany.doangiuakynt106.partials.BackgroundPanel;

//@Component
public class HomeUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(HomeUI.class.getName());
    
    private JButton btnAccountCreate;
    private JButton btnAccountInfo;
    private JButton btnSignOut;
    private JButton btnExit;
    private JPanel mainPanel;
    
    public HomeUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        // ======= Components =======
        btnAccountCreate = new JButton("Tạo tài khoản");
        btnAccountInfo = new JButton("Thông tin tài khoản");
        btnSignOut = new JButton("Đăng xuất");
        btnExit = new JButton("Thoát");

        // ======= Main panel (mờ như SignIn/SignUp) =======
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42, 180));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        // ======= Buttons Style =======
        stylePrimaryButton(btnAccountCreate);
        stylePrimaryButton(btnAccountInfo);

        styleSecondaryButton(btnSignOut);
        styleSecondaryButton(btnExit);

        // ===== Layout =====
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(btnAccountCreate, 250, 250, 260)
                .addComponent(btnAccountInfo, 250, 250, 260)
                .addGap(20)
                .addComponent(btnSignOut, 200, 200, 240)
                .addComponent(btnExit, 200, 200, 240)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(btnAccountCreate, 45, 45, 50)
                .addGap(25)
                .addComponent(btnAccountInfo, 45, 45, 50)
                .addGap(50)
                .addComponent(btnSignOut, 40, 40, 45)
                .addGap(15)
                .addComponent(btnExit, 40, 40, 45)
        );

        // ===== Event handlers =====
        btnAccountCreate.addActionListener(evt -> {
            this.dispose();
            AddUserUI.displayAddUserUI();
        });

        btnSignOut.addActionListener(evt -> {
            SignOut.SignOutAdmin();
            this.dispose();
            SignInUI.displaySignInUI();
        });

        btnExit.addActionListener(evt -> System.exit(0));

        // ===== Background =====
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");
        container.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        container.add(mainPanel, gbc);

        setContentPane(container);

        // ===== Window settings =====
        setTitle("Trang chủ");
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*setLocationRelativeTo(null);
        setSize(800, 600);*/     
    }

    // =======================================================================
    // STYLE FUNCTIONS
    // =======================================================================

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(0, 204, 255));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setBackground(new Color(51, 65, 85));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
    }

    /*public static void main(String args[]) {
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
    }*/
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
