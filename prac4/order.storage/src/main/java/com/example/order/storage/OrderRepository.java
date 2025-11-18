package com.example.order.storage;

import com.example.order.processing.OrderProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderRepository {
    private final List<OrderProcessor<?>> orders = Collections.synchronizedList(new ArrayList<>());

    public void add(OrderProcessor<?> order) {
        orders.add(order);
        System.out.println("[Storage] Order saved: " + order.getProduct().toString());
    }

    public List<OrderProcessor<?>> getOrders() {
        return orders;
    }
}
