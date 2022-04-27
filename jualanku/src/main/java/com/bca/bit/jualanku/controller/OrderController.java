package com.bca.bit.jualanku.controller;

import com.bca.bit.jualanku.model.*;
import com.bca.bit.jualanku.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ProductService productService;

    @PostMapping(value = "/buyer/order/create")
    @Transactional
    public String createOrder(@RequestParam("totalPrice") Long totalPrice, @RequestParam("cartId") Long cartId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();

        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletService.findWalletByBuyer(user);
        if (optionalWallet.isPresent()){
            wallet = optionalWallet.get();
        }

        Order order = new Order();
        order.setBuyer(user);
        order.setWallet(wallet);
        order.setTotalPrice(totalPrice);
        order.setDateCreated(new Timestamp(System.currentTimeMillis()));
        order.setDateUpdated(new Timestamp(System.currentTimeMillis()));

        Order ordered = orderService.createOrder(order);

        Cart cart = new Cart();
        Optional<Cart> optionalCart = cartService.findCartById(cartId);
        if(optionalCart.isPresent()){
            cart = optionalCart.get();
        }
        List<CartItem> cartItems = new ArrayList<>();
        cartItems =   cartItemService.findCartItemByCartId(cart);
        for(CartItem i : cartItems){
            OrderItem orderItem = new OrderItem(ordered, i.getProduct(), i.getProduct().getSeller(), user, i.getProductQty(), "Pending", new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()));
            orderItemService.createOrderItem(orderItem);

            Product product = i.getProduct();
            product.setStockOrdered(i.getProductQty());
            product.setStockAvailable(product.getStockAvailable() - i.getProductQty());
            productService.updateProduct(product);
        }

        wallet.setTotalBalance(wallet.getTotalBalance() - (totalPrice + 10000));
        walletService.updateWallet(wallet);

        cartItemService.deleteAllCartItem();

        return "redirect:/index";
    }

    @GetMapping(value = "/buyer/order")
    public ModelAndView orderAllBuyer(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems = orderItemService.findAllOrderItemByBuyer(user);
        modelAndView.addObject("orderItems", orderItems);

        Cart cart = new Cart();
        Optional<Cart> optionalCart = cartService.findCartByBuyerId(user);
        if(optionalCart.isPresent()){
            cart = optionalCart.get();
            Integer cartItem = cartItemService.countCartItemByCartId(cart);
            modelAndView.addObject("cart", cart);
            modelAndView.addObject("countCartItem", cartItem);
        }
        CartItem cartItem = new CartItem();
        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletService.findWalletByBuyer(user);
        if (optionalWallet.isPresent()){
            wallet = optionalWallet.get();
        }
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("/buyer/order");
        return modelAndView;
    }

    @GetMapping(value = "/seller/order")
    public ModelAndView orderAllSeller(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems = orderItemService.findAllOrderItemBySeller(user);
        modelAndView.addObject("orderItems", orderItems);
        modelAndView.setViewName("/seller/order");
        return modelAndView;
    }

    @PostMapping(value = "/seller/order/{orderItemId}/decline")
    @Transactional
    public String declineOrder(@PathVariable Long orderItemId){
        OrderItem orderItem = new OrderItem();
        Product product = new Product();
        Optional<OrderItem> optionalOrderItem = orderItemService.findOrderItemById(orderItemId);
        if(optionalOrderItem.isPresent()){
            orderItem = optionalOrderItem.get();
            orderItem.setStatus("Canceled");
            orderItemService.updateOrderItem(orderItem);

            product = orderItem.getProduct();
            product.setStockAvailable(product.getStockAvailable() + orderItem.getProductQty());
            product.setStockOrdered(product.getStockOrdered() - orderItem.getProductQty());
            productService.updateProduct(product);

            Wallet wallet = orderItem.getOrder().getWallet();
            wallet.setTotalBalance(wallet.getTotalBalance() + (orderItem.getProductQty() * orderItem.getProduct().getPrice()));
            walletService.updateWallet(wallet);
        }
        return "redirect:/seller/order";
    }

    @PostMapping(value = "/seller/order/{orderItemId}/accept")
    @Transactional
    public String acceptOrder(@PathVariable Long orderItemId){
        OrderItem orderItem = new OrderItem();
        Product product = new Product();
        Optional<OrderItem> optionalOrderItem = orderItemService.findOrderItemById(orderItemId);
        if(optionalOrderItem.isPresent()){
            orderItem = optionalOrderItem.get();
            orderItem.setStatus("Sent");
            orderItemService.updateOrderItem(orderItem);

            product = orderItem.getProduct();
            product.setStockSold(product.getStockSold() + orderItem.getProductQty());
            product.setStockOrdered(product.getStockOrdered() - orderItem.getProductQty());
            productService.updateProduct(product);
        }
        return "redirect:/seller/order";
    }
}
