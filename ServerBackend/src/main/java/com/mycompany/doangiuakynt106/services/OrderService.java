package com.mycompany.doangiuakynt106.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.mycompany.doangiuakynt106.api.dto.OrderDto;
import com.mycompany.doangiuakynt106.api.dto.OrderItem;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class OrderService {

    private final Firestore db;
    private final RtdbService rtdb;

    public OrderService(Firestore db, RtdbService rtdb) {
        this.db = db;
        this.rtdb = rtdb;
    }

    public OrderDto createOrder(OrderDto order) throws Exception {
        if (order.getMachineId() == null || order.getMachineId().isBlank()) {
            throw new IllegalArgumentException("machineId_required");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("items_required");
        }

        int total = 0;
        for (OrderItem it : order.getItems()) {
            total += it.getQty() * it.getPrice();
        }
        order.setTotal(total);
        order.setStatus(OrderDto.Status.NEW);
        order.setCreatedAt(Instant.now());

        Map<String, Object> doc = new HashMap<>();
        doc.put("machineId", order.getMachineId());
        doc.put("userId", order.getUserId());
        doc.put("username", order.getUsername());
        doc.put("note", order.getNote());
        doc.put("total", order.getTotal());
        doc.put("status", order.getStatus().name());
        doc.put("createdAt", order.getCreatedAt().toString());

        List<Map<String, Object>> items = new ArrayList<>();
        for (OrderItem it : order.getItems()) {
            items.add(Map.of(
                    "name", it.getName(),
                    "qty", it.getQty(),
                    "price", it.getPrice()
            ));
        }
        doc.put("items", items);

        ApiFuture<DocumentReference> write = db.collection("Orders").add(doc);
        order.setId(write.get().getId());

        // publish realtime cho server/nhân viên
        rtdb.upsertOrder(order);
        return order;
    }

    public List<OrderDto> listOrders(String status) throws Exception {
        CollectionReference col = db.collection("Orders");
        Query q = col.orderBy("createdAt", Query.Direction.DESCENDING);
        if (status != null && !status.isBlank()) {
            q = q.whereEqualTo("status", status);
        }
        ApiFuture<QuerySnapshot> future = q.get();
        List<OrderDto> out = new ArrayList<>();
        for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
            out.add(fromDoc(doc));
        }
        return out;
    }

    public OrderDto updateStatus(String orderId, OrderDto.Status status) throws Exception {
        DocumentReference ref = db.collection("Orders").document(orderId);
        ApiFuture<WriteResult> wr = ref.update(Map.of(
                "status", status.name()
        ));
        wr.get();

        DocumentSnapshot snap = ref.get().get();
        OrderDto out = fromDoc(snap);
        rtdb.upsertOrder(out);
        return out;
    }

    private OrderDto fromDoc(DocumentSnapshot doc) {
        OrderDto o = new OrderDto();
        o.setId(doc.getId());
        o.setMachineId(doc.getString("machineId"));
        o.setUserId(doc.getString("userId"));
        o.setUsername(doc.getString("username"));
        o.setNote(doc.getString("note"));
        Long total = doc.getLong("total");
        o.setTotal(total == null ? 0 : total.intValue());
        String st = doc.getString("status");
        if (st != null) {
            try { o.setStatus(OrderDto.Status.valueOf(st)); } catch (Exception ignored) {}
        }
        String createdAt = doc.getString("createdAt");
        if (createdAt != null) {
            try { o.setCreatedAt(Instant.parse(createdAt)); } catch (Exception ignored) {}
        }

        Object itemsObj = doc.get("items");
        if (itemsObj instanceof List<?> list) {
            List<OrderItem> items = new ArrayList<>();
            for (Object it : list) {
                if (it instanceof Map<?, ?> m) {
                    OrderItem oi = new OrderItem();
                    oi.setName(String.valueOf(m.get("name")));
                    Object q = m.get("qty");
                    Object p = m.get("price");
                    oi.setQty(q instanceof Number n ? n.intValue() : 0);
                    oi.setPrice(p instanceof Number n ? n.intValue() : 0);
                    items.add(oi);
                }
            }
            o.setItems(items);
        }
        return o;
    }
}
