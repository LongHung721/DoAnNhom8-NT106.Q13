package com.netcafe.backend.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.netcafe.backend.dto.ErrorResponse;
import com.netcafe.backend.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final Firestore db;
    private final String ordersCollection;
    private final SimpMessagingTemplate messaging;

    public OrderController(Firestore db,
                           @Value("${app.firestore.ordersCollection}") String ordersCollection,
                           SimpMessagingTemplate messaging) {
        this.db = db;
        this.ordersCollection = ordersCollection;
        this.messaging = messaging;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        try {
            body.putIfAbsent("status", "PENDING");
            body.putIfAbsent("createdAt", Instant.now().toEpochMilli());
            DocumentReference ref = db.collection(ordersCollection).document();
            ref.set(body).get();

            Map<String, Object> event = Map.of("event", "NEW_ORDER", "orderId", ref.getId(), "data", body);
            messaging.convertAndSend("/topic/orders", event);

            return ResponseEntity.ok(Map.of("id", ref.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Server error"));
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> pending() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(ordersCollection)
                .whereEqualTo("status", "PENDING")
                .get();
        List<Map<String, Object>> orders = new ArrayList<>();
        for (DocumentSnapshot d : future.get().getDocuments()) {
            Map<String, Object> m = new HashMap<>(d.getData() == null ? Map.of() : d.getData());
            m.put("id", d.getId());
            orders.add(m);
        }
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/fulfill")
    public ResponseEntity<?> fulfill(@PathVariable String id) {
        try {
            db.collection(ordersCollection).document(id).update("status", "FULFILLED").get();
            return ResponseEntity.ok(Map.of("status", "OK"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Server error"));
        }
    }
}
