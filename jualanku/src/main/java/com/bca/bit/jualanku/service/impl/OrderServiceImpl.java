package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.Order;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.repository.OrderRepository;
import com.bca.bit.jualanku.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            return order;
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    @Override
    public List<Order> findOrderByBuyer(User buyer) {
        List<Order> orders = orderRepository.findByBuyer(buyer);
        return orders;
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
