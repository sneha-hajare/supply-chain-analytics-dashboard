
package com.example.supplychain.service;

import com.example.supplychain.entity.SupplyOrder;
import com.example.supplychain.repository.SupplyOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyOrderService {

    private final SupplyOrderRepository orderRepository;

    public SupplyOrderService(SupplyOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<SupplyOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public SupplyOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public SupplyOrder saveOrder(SupplyOrder order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
