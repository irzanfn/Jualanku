package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.User;

import java.util.Optional;

public interface CartService {
    // create cart
    Cart createCart(Cart cart);

    // find cart by id
    Optional<Cart> findCartById(Long id);

    // find cart by buyerId
    Optional<Cart> findCartByBuyerId(User user);

    // update cart
    Cart updateCart(Cart cart);

    // delete cart
    void deleteCart(Long id);

}
