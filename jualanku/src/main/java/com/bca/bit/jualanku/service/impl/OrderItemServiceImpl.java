package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.OrderItem;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.repository.OrderItemRepository;
import com.bca.bit.jualanku.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public List<OrderItem> findAllOrderItemByBuyer(User buyer) {
        return orderItemRepository.findAllByBuyer(buyer);
    }

    @Override
    public List<OrderItem> findAllOrderItemBySeller(User seller) {
        return orderItemRepository.findAllBySeller(seller);
    }

    @Override
    public Optional<OrderItem> findOrderItemById(Long id) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        return orderItem;
    }

    @Override
    public List<OrderItem> findOrderItemByOrderId(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItemById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
