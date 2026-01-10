package com.mycompany.doangiuakynt106.api.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    public enum Status {
        NEW,
        ACCEPTED,
        DONE,
        CANCELED
    }

    private String id;
    private String machineId;
    private String userId;
    private String username;
    private List<OrderItem> items = new ArrayList<>();
    private String note;
    private int total;
    private Status status = Status.NEW;
    private Instant createdAt;

    public OrderDto() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
