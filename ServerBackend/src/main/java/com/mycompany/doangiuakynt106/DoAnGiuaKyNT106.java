/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.doangiuakynt106;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    public static void main(String[] args) { 
        ConfigurableApplicationContext context = new SpringApplicationBuilder(DoAnGiuaKyNT106.class)
                .headless(false) 
                .run(args);
                

        SwingUtilities.invokeLater(() -> {
            
            Preferences prefs = Preferences.userRoot().node("DAGKNT106");
            String idToken = prefs.get("idToken", null);

            
            if (idToken != null && !idToken.isEmpty()) {
                HomeUI.displayHomeUI();
            } else {
                SignInUI.displaySignInUI();
            }
        });
    }
}