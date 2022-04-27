package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.OrderItem;
import com.bca.bit.jualanku.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderItemService {

    // Find All Order Item
    List<OrderItem> findAllOrderItemByBuyer(User buyer);

    List<OrderItem> findAllOrderItemBySeller(User seller);

    // Find Order Item by Id
    Optional<OrderItem> findOrderItemById(Long id);

    // Find Order Item by Order Id
    List<OrderItem> findOrderItemByOrderId(Long orderId);

    // Save Order Item
    OrderItem createOrderItem(OrderItem orderItem);

    // Update Order Item
    OrderItem updateOrderItem(OrderItem orderItem);

    // Delete Order Item by Id
    void deleteOrderItemById(Long id);
}
