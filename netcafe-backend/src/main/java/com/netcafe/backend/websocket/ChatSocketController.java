package com.netcafe.backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.Map;

@Controller
public class ChatSocketController {

    private final SimpMessagingTemplate messaging;

    public ChatSocketController(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    // Client send to /app/chat.send ; server broadcasts to /topic/chat
    @MessageMapping("/chat.send")
    public void send(@Payload Map<String, Object> payload) {
        payload.putIfAbsent("createdAt", Instant.now().toEpochMilli());
        Map<String, Object> event = Map.of("event", "CHAT_MESSAGE", "data", payload);
        messaging.convertAndSend("/topic/chat", event);
    }
}
