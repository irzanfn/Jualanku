package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.CartItem;
import com.bca.bit.jualanku.model.Product;
import com.bca.bit.jualanku.repository.CartItemRepository;
import com.bca.bit.jualanku.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;


    @Override
    public List<CartItem> findAllCartItem() {
        return cartItemRepository.findAll();
    }

    @Override
    public Optional<CartItem> findCartItemById(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if(cartItem.isPresent()){
            return cartItem;
        } else {
            throw new RuntimeException("Cart Item not found");
        }
    }

    @Override
    public Optional<CartItem> findCartItemByProductId(Product product) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProduct(product);
        return optionalCartItem;
    }

    @Override
    public List<CartItem> findCartItemByCartId(Cart cart) {
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public Integer countCartItemByCartId(Cart cart) {
        return cartItemRepository.countByCartId(cart);
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setDateCreated(new Timestamp(System.currentTimeMillis()));
        cartItem.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        cartItem.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> updateAllCartItem(List<CartItem> cartItems) {
        return cartItemRepository.saveAll(cartItems);
    }

    @Override
    public void deleteAllCartItem() {
        cartItemRepository.deleteAll();
    }

    @Override
    public void deleteCartItemById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
