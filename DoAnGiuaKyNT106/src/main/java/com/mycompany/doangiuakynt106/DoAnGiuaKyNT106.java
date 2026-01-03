/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.doangiuakynt106;

import java.util.prefs.Preferences;
// Bỏ import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType; // Thêm import này
import org.springframework.context.ConfigurableApplicationContext; // Thêm import này
import javax.swing.SwingUtilities;

/**
 *
 * @author ASUS
 */
@SpringBootApplication
public class DoAnGiuaKyNT106 {

    public static void main(String[] args) { // Bỏ 'throws IOException'

        // 1. Khởi chạy Spring Context
        // Sử dụng SpringApplicationBuilder để cấu hình
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DoAnGiuaKyNT106.class)
                .headless(false) // QUAN TRỌNG: Phải set false để Swing/AWT chạy
                .web(WebApplicationType.NONE) // QUAN TRỌNG: Báo Spring đây không phải ứng dụng web
                .run(args);

        // 2. Khởi chạy GUI trên luồng Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            // Lấy thông tin
            Preferences prefs = Preferences.userRoot().node("DAGKNT106");
            String idToken = prefs.get("idToken", null);

            // 3. LẤY UI TỪ SPRING CONTEXT (thay vì 'new')
            // Điều này đảm bảo các @Autowired bên trong UI hoạt động
            if (idToken != null && !idToken.isEmpty()) {
                // Lấy bean HomeUI từ Spring
                HomeUI homeUI = context.getBean(HomeUI.class);
                homeUI.setVisible(true);
            } else {
                // Lấy bean SignInUI từ Spring
                SignInUI signInUI = context.getBean(SignInUI.class);
                signInUI.setVisible(true);
            }
        });
    }
}