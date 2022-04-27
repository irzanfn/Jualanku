package com.bca.bit.jualanku.dto;

import com.bca.bit.jualanku.model.CartItem;

import java.util.List;

public class CartItemDto {
    private List<CartItem> cartItems;

    public CartItemDto() {

    }

    public CartItemDto(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
