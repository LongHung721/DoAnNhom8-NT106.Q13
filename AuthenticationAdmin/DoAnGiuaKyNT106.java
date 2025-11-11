/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.doangiuakynt106;
import java.util.prefs.Preferences;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.SwingUtilities;



/**
 *
 * @author ASUS
 */
@SpringBootApplication
public class DoAnGiuaKyNT106 {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DoAnGiuaKyNT106.class, args);
        
        Preferences prefs = Preferences.userRoot().node("DAGKNT106");
        String idToken = prefs.get("idToken", null);
        
        SwingUtilities.invokeLater(() -> {
            if (idToken != null && !idToken.isEmpty()) {
                new HomeUI().setVisible(true);
            } else {
                new SignInUI().setVisible(true);
            }
        });
    }
}
