package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.CartItem;
import com.bca.bit.jualanku.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByProduct(Product product);

    @Query(value = "SELECT COUNT(*) FROM T_CART_ITEM WHERE CART_ID  = :cartId", nativeQuery = true)
    Integer countByCartId(@Param("cartId") Cart cartId);


}