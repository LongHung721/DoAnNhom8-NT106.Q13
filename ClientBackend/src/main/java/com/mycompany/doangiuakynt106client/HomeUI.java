package com.mycompany.doangiuakynt106client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.google.firebase.database.ValueEventListener;
import com.mycompany.doangiuakynt106client.firebase.FirebaseRtdbClient;
import java.util.Map;

/**
 * HomeUI mới: Thiết kế dạng Dashboard nhỏ gọn, độc lập ở góc màn hình.
 * Thay thế hoàn toàn giao diện Home cũ.
 */
public class HomeUI extends JFrame {

    private JLabel lblTimePlayed, lblTimeRemaining;
    private int secondsPlayed = 0;
    private int secondsRemaining;
    private Timer timer;

    private final String machineId;
    private boolean warned5min = false;
    private ValueEventListener machineListener;
    private ValueEventListener machineListener;

    public HomeUI(String username, String userType, String balance, int initialRemainingSeconds, String machineId) {
        this.secondsRemaining = initialRemainingSeconds;
        this.machineId = machineId;
        initComponents(username, userType, balance);
    }

    private void initComponents(String username, String userType, String balance) {
        // Cấu hình JFrame dạng Dashboard nổi
        setUndecorated(true); 
        setBackground(new Color(0, 0, 0, 0)); 
        setAlwaysOnTop(true); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel chính bo góc Neon
        RoundedPanel mainContainer = new RoundedPanel(25, new Color(15, 23, 42, 235), new Color(0, 204, 255), 2);
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // --- HEADER ---
        JLabel lblHeader = new JLabel("CYBER STATION", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHeader.setForeground(new Color(0, 204, 255));
        mainContainer.add(lblHeader, BorderLayout.NORTH);

        // --- BODY: Thông tin tài khoản ---
        JPanel infoBody = new JPanel(new GridLayout(5, 2, 5, 10));
        infoBody.setOpaque(false);
        infoBody.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        addInfoRow(infoBody, "Tài khoản:", username);
        addInfoRow(infoBody, "Hạng:", userType);

        lblTimePlayed = new JLabel(formatTime(secondsPlayed));
        styleValueLabel(lblTimePlayed);
        addCustomRow(infoBody, "Đã dùng:", lblTimePlayed);

        lblTimeRemaining = new JLabel(formatTime(secondsRemaining));
        styleValueLabel(lblTimeRemaining);
        lblTimeRemaining.setForeground(new Color(0, 255, 127)); 
        addCustomRow(infoBody, "Còn lại:", lblTimeRemaining);

        addInfoRow(infoBody, "Số dư:", balance + "đ");

        mainContainer.add(infoBody, BorderLayout.CENTER);

        // --- FOOTER: Các nút chức năng ---
        JPanel footer = new JPanel(new GridLayout(2, 1, 0, 8));
        footer.setOpaque(false);

        // Nút Đặt dịch vụ (Mở FoodOrderUI)
        JButton btnOrder = new JButton("ĐẶT DỊCH VỤ");
        styleActionColor(btnOrder, new Color(0, 204, 255));
        btnOrder.addActionListener(e -> new FoodOrderUI(machineId).setVisible(true));

        // Panel chứa 2 nút nhỏ bên dưới
        JPanel subButtons = new JPanel(new GridLayout(1, 2, 8, 0));
        subButtons.setOpaque(false);

        JButton btnMinimize = new JButton("ẨN");
        styleActionColor(btnMinimize, new Color(51, 65, 85));
        btnMinimize.addActionListener(e -> setExtendedState(JFrame.ICONIFIED));

        JButton btnLogout = new JButton("ĐĂNG XUẤT");
        styleActionColor(btnLogout, new Color(255, 82, 82));
        btnLogout.addActionListener(e -> {
            timer.stop();
            FirebaseRtdbClient.stopListenMachine(machineId, machineListener);
            try {
                ApiClient.lockMachine(machineId);
            } catch (Exception ignored) {}
            dispose();
            new SignInUI().setVisible(true);
        });

        subButtons.add(btnMinimize);
        subButtons.add(btnLogout);

        footer.add(btnOrder);
        footer.add(subButtons);
        mainContainer.add(footer, BorderLayout.SOUTH);

        add(mainContainer);
        setSize(300, 450);

        // Vị trí: Góc trên bên phải
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth() - 20, 40);

        // Timer đếm giờ
        timer = new Timer(1000, (ActionEvent e) -> {
            secondsPlayed++;
            lblTimePlayed.setText(formatTime(secondsPlayed));
            lblTimeRemaining.setText(formatTime(secondsRemaining));

            // Còn <5p thì báo 1 lần
            if (!warned5min && secondsRemaining > 0 && secondsRemaining <= 300) {
                warned5min = true;
                JOptionPane.showMessageDialog(this, "Cảnh báo: còn dưới 5 phút sẽ tự khóa máy!", "Sắp hết giờ", JOptionPane.WARNING_MESSAGE);
            }

            if (secondsRemaining <= 0) {
                timer.stop();
                FirebaseRtdbClient.stopListenMachine(machineId, machineListener);
                JOptionPane.showMessageDialog(this, "Hết giờ - máy đã bị khóa.");
                dispose();
                new SignInUI().setVisible(true);
            }
        });
        timer.start();

        // ===== Realtime (Firebase RTDB): nghe trạng thái máy để cập nhật thời gian còn lại / tự khóa =====
        machineListener = FirebaseRtdbClient.listenMachine(machineId, this::onMachineRtdbChange);
    }

    private void onMachineRtdbChange(Map<String, Object> machine) {
        SwingUtilities.invokeLater(() -> {
            try {
                Object rem = machine.get("remainingSeconds");
                if (rem instanceof Number n) {
                    secondsRemaining = Math.max(0, n.intValue());
                    lblTimeRemaining.setText(formatTime(secondsRemaining));
                }

                Object st = machine.get("status");
                String status = st == null ? "" : String.valueOf(st);

                // Còn <5p thì báo 1 lần
                if (!warned5min && secondsRemaining > 0 && secondsRemaining <= 300) {
                    warned5min = true;
                    JOptionPane.showMessageDialog(this, "Cảnh báo: còn dưới 5 phút sẽ tự khóa máy!", "Sắp hết giờ", JOptionPane.WARNING_MESSAGE);
                }

                // Nếu backend đã khoá máy -> tự đăng xuất
                if (secondsRemaining <= 0 || "LOCKED".equalsIgnoreCase(status)) {
                    timer.stop();
                    FirebaseRtdbClient.stopListenMachine(machineId, machineListener);
                    JOptionPane.showMessageDialog(this, "Hết giờ - máy đã bị khóa.");
                    dispose();
                    new SignInUI().setVisible(true);
                }
            } catch (Exception ignored) {
            }
        });
    }

    private void addInfoRow(JPanel container, String key, String value) {
        JLabel k = new JLabel(key);
        k.setForeground(Color.LIGHT_GRAY);
        k.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel v = new JLabel(value);
        styleValueLabel(v);
        container.add(k); container.add(v);
    }

    private void addCustomRow(JPanel container, String key, JLabel component) {
        JLabel k = new JLabel(key);
        k.setForeground(Color.LIGHT_GRAY);
        k.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        container.add(k); container.add(component);
    }

    private void styleValueLabel(JLabel lbl) {
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void styleActionColor(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(bgColor.equals(new Color(0, 255, 127)) || bgColor.equals(new Color(0, 204, 255)) ? Color.BLACK : Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
    }

    private String formatTime(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = (totalSeconds % 3600) / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}