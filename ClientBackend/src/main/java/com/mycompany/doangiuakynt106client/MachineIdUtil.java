package com.mycompany.doangiuakynt106client;

import java.net.InetAddress;

/**
 * Xác định machineId cho client.
 * - Mặc định: lấy hostname.
 * - Nếu hostname quá dài/ký tự lạ, vẫn dùng được vì server map theo string.
 */
public class MachineIdUtil {
    public static String getMachineId() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "CLIENT";
        }
    }
}
