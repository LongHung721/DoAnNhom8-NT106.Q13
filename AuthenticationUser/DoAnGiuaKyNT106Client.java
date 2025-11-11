/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.doangiuakynt106client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.prefs.Preferences;
import java.time.Duration;
//import org.json.JSONObject;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.SwingUtilities;
/**
 *
 * @author ASUS
 */
public class DoAnGiuaKyNT106Client {
    public static void main(String[] args) {
        Preferences prefs = Preferences.userRoot().node("DAGKNT106CLIENT");
        String token = prefs.get("token", null);
        
        SwingUtilities.invokeLater(() -> {
            if (token != null && !token.isEmpty()) {
                new HomeUI().setVisible(true);
            } else {
                new SignInUI().setVisible(true);
            }
        });
    }
}
