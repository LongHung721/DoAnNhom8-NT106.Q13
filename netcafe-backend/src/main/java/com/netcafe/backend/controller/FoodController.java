package com.netcafe.backend.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.netcafe.backend.model.FoodItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final Firestore db;
    private final String foodsCollection;

    public FoodController(Firestore db, @Value("${app.firestore.foodsCollection}") String foodsCollection) {
        this.db = db;
        this.foodsCollection = foodsCollection;
    }

    @GetMapping
    public ResponseEntity<?> list() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(foodsCollection).get();
        List<Map<String, Object>> items = new ArrayList<>();
        for (DocumentSnapshot d : future.get().getDocuments()) {
            Map<String, Object> m = new HashMap<>(d.getData() == null ? Map.of() : d.getData());
            m.put("id", d.getId());
            items.add(m);
        }
        return ResponseEntity.ok(items);
    }
}
