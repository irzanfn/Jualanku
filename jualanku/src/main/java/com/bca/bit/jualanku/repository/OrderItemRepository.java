package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.OrderItem;
import com.bca.bit.jualanku.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderId(Long orderId);

    List<OrderItem> findAllByBuyer(User buyer);

    List<OrderItem> findAllBySeller(User seller);
}