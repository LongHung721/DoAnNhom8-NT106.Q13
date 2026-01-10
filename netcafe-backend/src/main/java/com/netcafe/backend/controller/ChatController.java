package com.netcafe.backend.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.netcafe.backend.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final Firestore db;
    private final String chatCollection;
    private final SimpMessagingTemplate messaging;

    public ChatController(Firestore db,
                          @Value("${app.firestore.chatCollection}") String chatCollection,
                          SimpMessagingTemplate messaging) {
        this.db = db;
        this.chatCollection = chatCollection;
        this.messaging = messaging;
    }

    @GetMapping("/messages")
    public ResponseEntity<?> listLast(@RequestParam(defaultValue = "50") int limit) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(chatCollection)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .limit(limit)
                .get();
        List<Map<String, Object>> msgs = new ArrayList<>();
        for (DocumentSnapshot d : future.get().getDocuments()) {
            Map<String, Object> m = new HashMap<>(d.getData() == null ? Map.of() : d.getData());
            m.put("id", d.getId());
            msgs.add(m);
        }
        Collections.reverse(msgs);
        return ResponseEntity.ok(msgs);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> send(@RequestBody Map<String, Object> body) {
        try {
            body.putIfAbsent("createdAt", Instant.now().toEpochMilli());
            DocumentReference ref = db.collection(chatCollection).document();
            ref.set(body).get();

            Map<String, Object> event = Map.of("event", "CHAT_MESSAGE", "id", ref.getId(), "data", body);
            messaging.convertAndSend("/topic/chat", event);

            return ResponseEntity.ok(Map.of("id", ref.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Server error"));
        }
    }
}
