package com.bca.bit.jualanku.controller;

import com.bca.bit.jualanku.dto.CartItemDto;
import com.bca.bit.jualanku.dto.CreditCardDto;
import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.CartItem;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.model.Wallet;
import com.bca.bit.jualanku.service.CartItemService;
import com.bca.bit.jualanku.service.CartService;
import com.bca.bit.jualanku.service.UserService;
import com.bca.bit.jualanku.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class WalletController {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @GetMapping(value = "/buyer/wallet/topup")
    public ModelAndView showTopupForm() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();

        Cart cart = new Cart();
        Optional<Cart> optionalCart = cartService.findCartByBuyerId(user);
        if(optionalCart.isPresent()){
            cart = optionalCart.get();
            Integer cartItem = cartItemService.countCartItemByCartId(cart);
            modelAndView.addObject("cart", cart);
            modelAndView.addObject("countCartItem", cartItem);
        }

        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletService.findWalletByBuyer(user);
        if (optionalWallet.isPresent()){
            wallet = optionalWallet.get();
        }

        CreditCardDto creditCardDto = new CreditCardDto();
        modelAndView.addObject("creditCard", creditCardDto);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("buyer/wallet");
        return modelAndView;
    }

    @PostMapping(value = "/buyer/wallet/topup")
    public String topupBalance(@ModelAttribute("creditCard") CreditCardDto creditCardDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        Wallet wallet = new Wallet();
        Optional<Wallet> optionalWallet = walletService.findWalletByBuyer(user);
        if (optionalWallet.isPresent()){
            wallet = optionalWallet.get();
        }
        System.out.println("===============");
        if(!creditCardDto.getCardNumber().equals(null)){
            wallet.setTotalBalance(wallet.getTotalBalance() + creditCardDto.getBalance());
            walletService.updateWallet(wallet);
        }
        System.out.println("===============");
        return "redirect:/index";
    }


}
