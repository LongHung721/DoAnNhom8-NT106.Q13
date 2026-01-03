package com.mycompany.doangiuakynt106client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UserStatusDialog extends JDialog {

    private JLabel lblTimePlayed, lblTimeRemaining;
    private int secondsPlayed = 0;
    private int secondsRemaining;
    private Timer timer;

    public UserStatusDialog(JFrame parent, String username, String userType, String balance, int initialRemainingSeconds) {
        super(parent, false); 
        this.secondsRemaining = initialRemainingSeconds;
        initComponents(username, userType, balance);
    }

    private void initComponents(String username, String userType, String balance) {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); 
        setAlwaysOnTop(true); 

        // Sử dụng RoundedPanel (Đảm bảo file này đã có trong project Client)
        RoundedPanel mainContainer = new RoundedPanel(25, new Color(15, 23, 42, 230), new Color(0, 204, 255), 2);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblHeader = new JLabel("TRẠM MÁY TRẠM", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblHeader.setForeground(new Color(0, 204, 255));
        mainContainer.add(lblHeader, BorderLayout.NORTH);

        JPanel infoBody = new JPanel(new GridLayout(5, 2, 10, 12));
        infoBody.setOpaque(false);
        infoBody.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        addInfoRow(infoBody, "Tài khoản:", username);
        addInfoRow(infoBody, "Nhóm khách:", userType);

        lblTimePlayed = new JLabel(formatTime(secondsPlayed));
        styleValueLabel(lblTimePlayed);
        addCustomRow(infoBody, "Đã chơi:", lblTimePlayed);

        lblTimeRemaining = new JLabel(formatTime(secondsRemaining));
        styleValueLabel(lblTimeRemaining);
        lblTimeRemaining.setForeground(new Color(0, 255, 127)); 
        addCustomRow(infoBody, "Thời gian còn:", lblTimeRemaining);

        addInfoRow(infoBody, "Số dư tiền:", balance + " đ");

        mainContainer.add(infoBody, BorderLayout.CENTER);

        JButton btnMinimize = new JButton("ẨN BẢNG");
        styleButton(btnMinimize);
        btnMinimize.addActionListener(e -> setVisible(false));
        mainContainer.add(btnMinimize, BorderLayout.SOUTH);

        add(mainContainer);
        setSize(320, 400);

        // Đặt ở góc phải màn hình
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth() - 20, 50);

        timer = new Timer(1000, (ActionEvent e) -> {
            secondsPlayed++;
            if (secondsRemaining > 0) secondsRemaining--;
            lblTimePlayed.setText(formatTime(secondsPlayed));
            lblTimeRemaining.setText(formatTime(secondsRemaining));
        });
        timer.start();
    }

    private void addInfoRow(JPanel container, String key, String value) {
        JLabel k = new JLabel(key);
        k.setForeground(Color.LIGHT_GRAY);
        JLabel v = new JLabel(value);
        styleValueLabel(v);
        container.add(k); container.add(v);
    }

    private void addCustomRow(JPanel container, String key, JLabel component) {
        JLabel k = new JLabel(key);
        k.setForeground(Color.LIGHT_GRAY);
        container.add(k); container.add(component);
    }

    private void styleValueLabel(JLabel lbl) {
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleButton(JButton btn) {
        btn.setBackground(new Color(51, 65, 85));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
    }

    private String formatTime(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = (totalSeconds % 3600) / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    // ==========================================================
    // HÀM MAIN ĐỂ CHẠY THỬ (TEST UI)
    // ==========================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {}

        EventQueue.invokeLater(() -> {
            // Chạy thử với dữ liệu giả lập
            UserStatusDialog testPopup = new UserStatusDialog(
                null, 
                "ADMIN_TEST", 
                "Hội viên VIP", 
                "500.000", 
                10800 // 3 tiếng
            );
            testPopup.setVisible(true);
        });
    }
}