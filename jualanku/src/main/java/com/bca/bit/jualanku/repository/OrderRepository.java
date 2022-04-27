package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.Order;
import com.bca.bit.jualanku.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer(User buyer);
}