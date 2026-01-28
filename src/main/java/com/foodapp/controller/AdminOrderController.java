package com.foodapp.controller;

import com.foodapp.model.Order;
import com.foodapp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    // 🟢 VIEW ALL ORDERS (ADMIN)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // 🟡 UPDATE ORDER STATUS
    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}
