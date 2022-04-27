package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM T_CART WHERE BUYER_ID = :buyer", nativeQuery = true)
    Optional<Cart> findByBuyerId(@RequestAttribute("buyer") User buyer);
}