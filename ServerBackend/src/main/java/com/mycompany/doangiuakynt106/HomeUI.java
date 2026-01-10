package com.mycompany.doangiuakynt106;

import java.awt.*;
import javax.swing.*;
import org.springframework.stereotype.Component;
import com.mycompany.doangiuakynt106.partials.BackgroundPanel;

public class HomeUI extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(HomeUI.class.getName());
    
    private JButton btnAccountCreate;
    private JButton btnManageMachines;
    private JButton btnSignOut;
    private JButton btnExit;
    private JPanel mainPanel;
    
    public HomeUI() {
        initComponents();
    }

    private void initComponents() {

        // ======= Khởi tạo Components =======
        btnAccountCreate = new JButton("Tạo tài khoản khách");
        btnManageMachines = new JButton("Quản lý máy trạm");
        btnSignOut = new JButton("Đăng xuất");
        btnExit = new JButton("Thoát hệ thống");

        // ======= Main panel (Nền mờ Cyber) =======
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(15, 23, 42, 190)); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // ======= Thiết lập Style cho các nút =======
        stylePrimaryButton(btnAccountCreate);
        stylePrimaryButton(btnManageMachines);
        styleSecondaryButton(btnSignOut);
        styleSecondaryButton(btnExit);

        // ======= Bố cục Layout =======
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(btnAccountCreate, 250, 250, 280)
                .addComponent(btnManageMachines, 250, 250, 280)
                .addGap(20)
                .addComponent(btnSignOut, 200, 200, 220)
                .addComponent(btnExit, 200, 200, 220)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(btnAccountCreate, 45, 45, 50)
                .addGap(20)
                .addComponent(btnManageMachines, 45, 45, 50)
                .addGap(40)
                .addComponent(btnSignOut, 40, 40, 45)
                .addGap(15)
                .addComponent(btnExit, 40, 40, 45)
        );

        // ======= Xử lý sự kiện =======
        btnAccountCreate.addActionListener(evt -> {
            new AddUserUI().setVisible(true);
            this.dispose();
        });

        btnManageMachines.addActionListener(evt -> {
            new ComputerManagementUI().setVisible(true);
            this.dispose();
        });

        btnSignOut.addActionListener(evt -> {
            SignOut.SignOutAdmin();
            this.dispose();
            new SignInUI().setVisible(true);
        });

        btnExit.addActionListener(evt -> System.exit(0));

        // ======= Thiết lập Background =======
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");
        container.setLayout(new GridBagLayout());
        container.add(mainPanel, new GridBagConstraints());
        setContentPane(container);

        // ======= THAY ĐỔI TẠI ĐÂY: Window Settings =======
        setTitle("Hệ thống Quản lý Quán Net");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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

    private void stylePrimaryButton(JButton btn) {
        btn.setBackground(new Color(0, 204, 255));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton btn) {
        btn.setBackground(new Color(51, 65, 85));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void displayHomeUI() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new HomeUI().setVisible(true));
    }
}