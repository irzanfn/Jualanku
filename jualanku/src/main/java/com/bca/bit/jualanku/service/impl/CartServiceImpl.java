package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.repository.CartRepository;
import com.bca.bit.jualanku.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart createCart(Cart cart) {
        cart.setTotalPrice(0);
        cart.setDateCreated(new Timestamp(System.currentTimeMillis()));
        cart.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findCartById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isPresent()){
            return cart;
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    @Override
    public Optional<Cart> findCartByBuyerId(User user) {
        Optional<Cart> cart = cartRepository.findByBuyerId(user);
        if(cart.isPresent()){
            return cart;
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    @Override
    public Cart updateCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

}
