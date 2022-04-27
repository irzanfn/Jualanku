package com.bca.bit.jualanku.controller;

import com.bca.bit.jualanku.dto.PasswordDto;
import com.bca.bit.jualanku.model.*;
import com.bca.bit.jualanku.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = null;
        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if(optionalUser.isPresent()){
            userExists = optionalUser.get();
        }
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            User user1 = userService.createUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

            Wallet wallet = new Wallet();
            wallet.setBuyer(user1);
            walletService.createWallet(wallet);

            Cart cart = new Cart();
            cart.setBuyer(user1);
            cartService.createCart(cart);

        }
        return modelAndView;
    }

    @GetMapping("/buyer")
    public ModelAndView buyerHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        List<Product> products = productService.findAllProducts();
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
        modelAndView.addObject("cartItem", cartItem);
        modelAndView.addObject("productList", products);
        modelAndView.addObject("userName", "Welcome " + user.getId() + "/" + user.getName()  + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Buyer Role");
        modelAndView.setViewName("buyer/home");
        return modelAndView;
    }

    @GetMapping("/seller")
    public ModelAndView sellerHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        List<Product> products = productService.findProductsBySeller(user);
        modelAndView.addObject("productList", products);
        modelAndView.addObject("userName", "Welcome " + user.getId() + "/" + user.getName()  + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Seller Role");
        modelAndView.setViewName("seller/home");
        return modelAndView;
    }

    @GetMapping("/buyer/profile")
    public ModelAndView buyerProfile(){
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
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("userDetails", user);
        modelAndView.setViewName("buyer/profile/details");
        return modelAndView;
    }

    @GetMapping("/buyer/profile/update")
    public ModelAndView buyerProfileUpdateForm(){
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
        modelAndView.addObject("wallet", wallet);
        modelAndView.addObject("userDetails", user);
        modelAndView.setViewName("buyer/profile/edit");
        return modelAndView;
    }

    @GetMapping("/buyer/profile/password")
    public ModelAndView buyerProfilePasswordForm(){
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
        PasswordDto passwordDto = new PasswordDto();
        modelAndView.addObject("passwordForm", passwordDto);
        modelAndView.addObject("wallet", wallet);
        modelAndView.setViewName("buyer/profile/password");
        return modelAndView;
    }

    @PostMapping("/buyer/profile/update")
    public String buyerProfileUpdate(@ModelAttribute("userDetails") User buyer){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        user.setName(buyer.getName());
        user.setPhone(buyer.getPhone());
        user.setAddress(buyer.getAddress());
        userService.updateUser(user);

        return "redirect:/buyer/profile";
    }

    @PostMapping("/buyer/profile/password")
    public ModelAndView buyerProfilePasswordUpdate(@ModelAttribute("passwordForm") PasswordDto passwordDto){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName()).get();
        if(bCryptPasswordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())){
            if(passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
                userService.updateUser(user);
                SecurityContextHolder.clearContext();
                modelAndView.setViewName("login");
                return modelAndView;
            } else {
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
                modelAndView.addObject("passwordForm", passwordDto);
                modelAndView.addObject("wallet", wallet);
                modelAndView.addObject("messageError", "New password and Confirm password doesn't match!");
                modelAndView.setViewName("buyer/profile/password");
                return modelAndView;
            }
        } else {
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
            modelAndView.addObject("passwordForm", passwordDto);
            modelAndView.addObject("wallet", wallet);
            modelAndView.addObject("messageError", "Wrong current password!");
            modelAndView.setViewName("buyer/profile/password");
            return modelAndView;
        }
    }

    @PostMapping("/resetpassword/{id}")
    public ModelAndView resetPassword(@PathVariable Long id, @ModelAttribute("passwordForm") PasswordDto passwordDto){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> optionalUser = userService.findUserById(id);
        User user = new User();
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            if(passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(passwordDto.getNewPassword()));
                user.setAttempt(0);
                user.setAccountNonBlocked("true");
                userService.updateUser(user);
                modelAndView.setViewName("login");
                return modelAndView;
            } else {
                modelAndView.addObject("messageError", "New password and Confirm password doesn't match!");
                modelAndView.setViewName("redirect:/resetpassword/"+id);
                return modelAndView;
            }
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }


}
