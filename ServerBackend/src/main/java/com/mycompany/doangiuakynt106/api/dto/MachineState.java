package com.mycompany.doangiuakynt106.api.dto;

import java.time.Instant;

public class MachineState {
    public enum Status {
        LOCKED,
        RUNNING,
        PAUSED
    }

    private String machineId;
    private Status status;
    private String userId;
    private String username;
    private long remainingSeconds;
    private Instant startedAt;
    private Instant updatedAt;

    public MachineState() {}

    public MachineState(String machineId) {
        this.machineId = machineId;
        this.status = Status.LOCKED;
        this.remainingSeconds = 0;
        this.updatedAt = Instant.now();
    }

    public String getMachineId() { return machineId; }
    public void setMachineId(String machineId) { this.machineId = machineId; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public long getRemainingSeconds() { return remainingSeconds; }
    public void setRemainingSeconds(long remainingSeconds) { this.remainingSeconds = remainingSeconds; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
