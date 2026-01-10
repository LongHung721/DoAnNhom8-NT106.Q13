package com.netcafe.backend.model;

import java.util.List;

public class Order {
    public String id;
    public String createdBy; // userId
    public String machineId;
    public String status; // PENDING, FULFILLED
    public long createdAt;
    public java.util.List<OrderLine> lines;

    public static class OrderLine {
        public String itemId;
        public String itemName;
        public int quantity;
        public Number unitPrice;
    }
}
