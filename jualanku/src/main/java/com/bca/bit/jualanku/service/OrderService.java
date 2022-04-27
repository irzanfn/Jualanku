package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.Order;
import com.bca.bit.jualanku.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    // create order
    Order createOrder(Order order);

    // find order by id
    Optional<Order> findOrderById(Long id);

    // find order by buyerId
    List<Order> findOrderByBuyer(User user);

    // update order
    Order updateOrder(Order order);

    // delete order
    void deleteOrder(Long id);

    List<Order> findAll();
}
