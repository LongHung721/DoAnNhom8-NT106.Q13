package com.mycompany.doangiuakynt106;

import javax.swing.*;
import java.awt.*;
import com.mycompany.doangiuakynt106.partials.BackgroundPanel;

public class AddUserUI extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AddUserUI.class.getName());

    private JTextField tffullName;
    private JTextField tfidCard;
    private JTextField tfuserName;
    private JPasswordField tfpassword;
    private JButton btnAddUser;
    private JButton btnExit;
    private JLabel lblAccCreate;
    private JLabel lblfullName;
    private JLabel lblidCard;
    private JLabel lbluserName;
    private JLabel lblpassword;
    private RoundedPanel mainPanel;

    public AddUserUI() {
        initComponents();
    }

    private void initComponents() {
        // ===== Khởi tạo các thành phần =====
        tffullName = new JTextField();
        tfidCard = new JTextField();
        tfuserName = new JTextField();
        tfpassword = new JPasswordField();
        lblAccCreate = new JLabel("TẠO TÀI KHOẢN", SwingConstants.CENTER);
        lblfullName = new JLabel("Họ tên:");
        lblidCard = new JLabel("Số CCCD:");
        lbluserName = new JLabel("Tên TK:");
        lblpassword = new JLabel("Mật khẩu:");
        btnAddUser = new JButton("Tạo");
        btnExit = new JButton("Quay lại");

        // ===== Main panel =====
        mainPanel = new RoundedPanel(
                30,
                new Color(15, 23, 42, 180), // nền mờ
                new Color(0, 204, 255),
                3
        );
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        lblAccCreate.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAccCreate.setForeground(new Color(0, 204, 255));

        Font lblFont = new Font("Segoe UI", Font.PLAIN, 14);
        lblfullName.setFont(lblFont); lblfullName.setForeground(Color.WHITE);
        lblidCard.setFont(lblFont); lblidCard.setForeground(Color.WHITE);
        lbluserName.setFont(lblFont); lbluserName.setForeground(Color.WHITE);
        lblpassword.setFont(lblFont); lblpassword.setForeground(Color.WHITE);

        Font tfFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color tfBg = new Color(30, 41, 59);
        Color tfFg = Color.WHITE;
        tffullName.setFont(tfFont); tffullName.setBackground(tfBg); tffullName.setForeground(tfFg); tffullName.setCaretColor(tfFg); tffullName.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));
        tfidCard.setFont(tfFont); tfidCard.setBackground(tfBg); tfidCard.setForeground(tfFg); tfidCard.setCaretColor(tfFg); tfidCard.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));
        tfuserName.setFont(tfFont); tfuserName.setBackground(tfBg); tfuserName.setForeground(tfFg); tfuserName.setCaretColor(tfFg); tfuserName.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));
        tfpassword.setFont(tfFont); tfpassword.setBackground(tfBg); tfpassword.setForeground(tfFg); tfpassword.setCaretColor(tfFg); tfpassword.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 255), 1, true));

        // ===== Nút =====
        btnAddUser.setBackground(new Color(0, 204, 255));
        btnAddUser.setForeground(Color.BLACK);
        btnAddUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAddUser.setFocusPainted(false);
        btnAddUser.setBorder(BorderFactory.createEmptyBorder(8,0,8,0));

        btnExit.setBackground(new Color(51, 65, 85));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(8,0,8,0));

        // ===== Bố cục panel =====
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(lblAccCreate)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblfullName)
                                        .addComponent(lblidCard)
                                        .addComponent(lbluserName)
                                        .addComponent(lblpassword))
                                .addGap(18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(tffullName, 250, 250, 300)
                                        .addComponent(tfidCard, 250, 250, 300)
                                        .addComponent(tfuserName, 250, 250, 300)
                                        .addComponent(tfpassword, 250, 250, 300)))
                        .addComponent(btnAddUser, 200, 200, 220)
                        .addComponent(btnExit, 200, 200, 220)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(lblAccCreate)
                        .addGap(30)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblfullName)
                                .addComponent(tffullName, 30,30,30))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblidCard)
                                .addComponent(tfidCard,30,30,30))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbluserName)
                                .addComponent(tfuserName,30,30,30))
                        .addGap(15)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lblpassword)
                                .addComponent(tfpassword,30,30,30))
                        .addGap(25)
                        .addComponent(btnAddUser, 38,38,38)
                        .addGap(15)
                        .addComponent(btnExit, 35,35,35)
        );

        // ===== Sự kiện =====
        btnAddUser.addActionListener(e -> onAddUser());
        btnExit.addActionListener(e -> onExit());

        // ===== Background full màn hình =====
        BackgroundPanel container = new BackgroundPanel("/images/Anh_5.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(mainPanel, gbc);

        setContentPane(container);
        setTitle("Tạo tài khoản");
        setUndecorated(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(false);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(800, 600);  
    }

    private void onAddUser() {
        if(AppFeature.AddUser(tffullName.getText(), tfidCard.getText(), tfuserName.getText(), new String(tfpassword.getPassword()))){
            JOptionPane.showMessageDialog(
                    this,
                    "Tạo tài khoản thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Tạo tài khoản thất bại!",
                    "Thông báo",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onExit() {
        this.dispose();
        HomeUI.displayHomeUI();
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(AddUserUI::new);
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

        EventQueue.invokeLater(() -> new AddUserUI().setVisible(true));
    }
    public static void displayAddUserUI(){
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

        EventQueue.invokeLater(() -> new AddUserUI().setVisible(true));
    }
}
