package com.mycompany.doangiuakynt106client;

import javax.swing.UIManager;

/**
 * Lớp khởi chạy chính của ứng dụng Client quản lý quán Net.
 */
public class DoAnGiuaKyNT106Client {

    public static void main(String[] args) {
        // 1. Thiết lập giao diện Nimbus để đồng bộ phong cách hiện đại/Cyber
        setupLookAndFeel();

        // 2. Khởi chạy màn hình Đăng nhập (SignInUI)
        java.awt.EventQueue.invokeLater(() -> {
            // Khởi tạo và hiển thị màn hình Login đầu tiên
            SignInUI loginScreen = new SignInUI();
            loginScreen.setVisible(true);
        });
    }

    /**
     * Thiết lập Look and Feel cho hệ thống.
     * Nimbus mang lại giao diện tối và các component bo góc đẹp hơn mặc định.
     */
    private static void setupLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Không thể thiết lập Nimbus Look and Feel. Sử dụng mặc định.");
        }
    }
}