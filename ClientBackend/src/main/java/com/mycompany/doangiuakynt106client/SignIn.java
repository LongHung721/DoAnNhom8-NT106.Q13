/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106client;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.prefs.Preferences;
import java.time.Duration;
import org.json.JSONObject;
import java.io.PrintWriter;
import java.io.StringWriter;
/**
 *
 * @author ASUS
 */
public class SignIn {
    /**
     * Trả về JSON body nếu đăng nhập OK; null nếu thất bại.
     */
    public static JSONObject SignInUser(String username, String password) {
        try {
            JSONObject res = ApiClient.login(username, password);
            int status = res.getInt("status");
            JSONObject body = res.getJSONObject("body");
            if (status == 200) {
                String token = body.getString("token");
                ApiClient.setToken(token);
                return body;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
