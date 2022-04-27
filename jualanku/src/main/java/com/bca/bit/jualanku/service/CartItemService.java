package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.CartItem;
import com.bca.bit.jualanku.model.Product;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    // Find All Cart Item
    List<CartItem> findAllCartItem();

    // Find Cart Item by Id
    Optional<CartItem> findCartItemById(Long id);

    // Fin Cart Item by Product Id
    Optional<CartItem> findCartItemByProductId(Product product);

    // Find Cart Item by Cart Id
    List<CartItem> findCartItemByCartId(Cart cart);

    Integer countCartItemByCartId(Cart cart);

    // Save Cart Item
    CartItem createCartItem(CartItem cartItem);

    // Update Cart Item
    CartItem updateCartItem(CartItem cartItem);

    // Update all Cart Item
    List<CartItem> updateAllCartItem(List<CartItem> cartItems);

    void deleteAllCartItem();

    // Delete Cart Item by Id
    void deleteCartItemById(Long id);
}
