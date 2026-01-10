package com.mycompany.doangiuakynt106client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * Client REST helper. Mục tiêu: kết nối Swing client với Spring Boot server.
 */
public class ApiClient {
    // Nếu server chạy máy khác, sửa IP/port ở đây.
    public static String BASE_URL = "http://localhost:8080";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    private static Preferences prefs() {
        return Preferences.userRoot().node("DAGKNT106CLIENT");
    }

    public static String getToken() {
        return prefs().get("token", null);
    }

    public static void setToken(String token) {
        prefs().put("token", token);
    }

    public static JSONObject postJson(String path, JSONObject body, boolean auth) throws Exception {
        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .timeout(Duration.ofSeconds(15))
                .header("Content-Type", "application/json; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(body == null ? "{}" : body.toString()));
        if (auth) {
            String token = getToken();
            if (token != null && !token.isBlank()) {
                b.header("Authorization", "Bearer " + token);
            }
        }

        HttpResponse<String> res = httpClient.send(b.build(), HttpResponse.BodyHandlers.ofString());
        JSONObject out = new JSONObject();
        out.put("status", res.statusCode());
        out.put("body", new JSONObject(res.body()));
        return out;
    }

    public static JSONObject getJson(String path, boolean auth) throws Exception {
        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .timeout(Duration.ofSeconds(15))
                .GET();
        if (auth) {
            String token = getToken();
            if (token != null && !token.isBlank()) {
                b.header("Authorization", "Bearer " + token);
            }
        }
        HttpResponse<String> res = httpClient.send(b.build(), HttpResponse.BodyHandlers.ofString());
        JSONObject out = new JSONObject();
        out.put("status", res.statusCode());
        out.put("body", new JSONObject(res.body()));
        return out;
    }

    public static JSONObject login(String username, String password) throws Exception {
        JSONObject payload = new JSONObject().put("username", username).put("password", password);
        return postJson("/api/auth/login", payload, false);
    }

    public static JSONObject startMachineSession(String machineId) throws Exception {
        return postJson("/api/machines/" + machineId + "/start", new JSONObject(), true);
    }

    public static JSONObject lockMachine(String machineId) throws Exception {
        return postJson("/api/machines/" + machineId + "/lock", new JSONObject(), true);
    }

    public static JSONObject submitOrder(String machineId, List<Map<String, Object>> items, String note) throws Exception {
        JSONArray arr = new JSONArray();
        for (Map<String, Object> it : items) {
            arr.put(new JSONObject(it));
        }
        JSONObject payload = new JSONObject()
                .put("machineId", machineId)
                .put("items", arr)
                .put("note", note == null ? "" : note);
        return postJson("/api/orders", payload, true);
    }
}
