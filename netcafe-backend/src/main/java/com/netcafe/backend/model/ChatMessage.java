package com.netcafe.backend.model;

public class ChatMessage {
    public String id;
    public String fromUser;
    public String toUser; // optional
    public String message;
    public long createdAt;
}
