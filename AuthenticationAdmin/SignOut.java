/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.doangiuakynt106;

import java.util.prefs.Preferences;

/**
 *
 * @author ASUS
 */
public class SignOut {
    public static void SignOutAdmin() {
        Preferences prefs = Preferences.userRoot().node("DAGKNT106");
        prefs.remove("idToken");
        prefs.remove("refreshToken");
        
        FirebaseConnection.DeleteFirebaseApp();
    }
}
