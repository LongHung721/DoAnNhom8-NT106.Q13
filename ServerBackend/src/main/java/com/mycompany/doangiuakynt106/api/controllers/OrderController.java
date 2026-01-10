package com.mycompany.doangiuakynt106.api.controllers;

import com.mycompany.doangiuakynt106.api.dto.OrderDto;
import com.mycompany.doangiuakynt106.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderDto order, HttpServletRequest req) {
        try {
            String username = (String) req.getAttribute("username");
            order.setUsername(username);
            OrderDto out = orderService.createOrder(order);
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", Map.of("message", e.getMessage(), "code", 400)));
        }
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(required = false) String status) {
        try {
            List<OrderDto> out = orderService.listOrders(status);
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", Map.of("message", "internal_error", "code", 500)));
        }
    }

    @PostMapping("/{orderId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String orderId, @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().body(Map.of("error", Map.of("message", "status_required", "code", 400)));
            }
            OrderDto out = orderService.updateStatus(orderId, OrderDto.Status.valueOf(status));
            return ResponseEntity.ok(out);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", Map.of("message", e.getMessage(), "code", 400)));
        }
    }
}
