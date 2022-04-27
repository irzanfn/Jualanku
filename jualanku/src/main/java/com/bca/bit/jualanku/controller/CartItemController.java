package com.bca.bit.jualanku.controller;


import com.bca.bit.jualanku.dto.CartItemDto;
import com.bca.bit.jualanku.dto.OrderItemDto;
import com.bca.bit.jualanku.model.*;
import com.bca.bit.jualanku.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartItemController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @PostMapping(value = "/buyer/cartItem/add")
    public String addCartItem(@ModelAttribute("cartItem") CartItem cartItem, @RequestParam("productId") Long productId, @RequestParam("cartId") Long cartId) {
        Product product = new Product();
        Optional<Product> optionalProduct = productService.findProductById(productId);
        if(optionalProduct.isPresent()){
            cartItem.setProduct(optionalProduct.get());
            CartItem cartItem1 = new CartItem();
            product = optionalProduct.get();
            Optional<CartItem> optionalCartItem = cartItemService.findCartItemByProductId(product);
            if(optionalCartItem.isEmpty()){
                Optional<Cart> optionalCart = cartService.findCartById(cartId);
                if(optionalCart.isPresent()){
                    cartItem.setCart(optionalCart.get());
                }
                cartItemService.createCartItem(cartItem);
            } else {
                cartItem1 = optionalCartItem.get();
                cartItem1.setProductQty(cartItem1.getProductQty() + cartItem.getProductQty());
                cartItemService.updateCartItem(cartItem1);
            }
        }
        return "redirect:/index";
    }

    @GetMapping(value = "/buyer/cart/{id}")
    public ModelAndView showCartItem(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        CartItemDto cartItemsForm = new CartItemDto();
        Cart cart = new Cart();
        Optional<Cart> optionalCart = cartService.findCartById(id);
        if(optionalCart.isPresent()){
            cart = optionalCart.get();
        }
        List<CartItem> cartItems = new ArrayList<>();
        cartItems =   cartItemService.findCartItemByCartId(cart);
        cartItemsForm.setCartItems(cartItems);
        Integer cartItem = cartItemService.countCartItemByCartId(cart);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();

        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletService.findWalletByBuyer(user);
        if (optionalWallet.isPresent()){
            wallet = optionalWallet.get();
        }
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("countCartItem", cartItem);
        modelAndView.addObject("cartItems", cartItems);
        modelAndView.addObject("form", cartItemsForm);
        modelAndView.addObject("cart", cart);
        modelAndView.setViewName("buyer/cart");
        return modelAndView;
    }

    @PostMapping(value = "/buyer/cartItem/update")
    public String updateCartItem(@ModelAttribute("form") CartItemDto form) {
        System.out.println("======================");
        Long cartId = 0L;
        for(CartItem cartItem : form.getCartItems()){
            cartItem.setDateUpdated(new Timestamp(System.currentTimeMillis()));
            cartItemService.updateCartItem(cartItem);
            cartId = cartItem.getCart().getId();
            System.out.println(cartItem);
        }
        return "redirect:/buyer/cart/"+cartId;
    }

    @PostMapping(value = "/buyer/cartItem/{id}/delete")
    public String deleteCartItem(@PathVariable Long id) {
        CartItem cartItem = new CartItem();
        Optional<CartItem> optionalCartItem = cartItemService.findCartItemById(id);
        if(optionalCartItem.isPresent()){
            cartItem = optionalCartItem.get();
            cartItemService.deleteCartItemById(id);
            return "redirect:/buyer/cart/"+cartItem.getCart().getId();
        }
        return "redirect:/index";
    }
}
