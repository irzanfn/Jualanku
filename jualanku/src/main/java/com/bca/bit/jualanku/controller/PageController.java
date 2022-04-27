package com.bca.bit.jualanku.controller;

import com.bca.bit.jualanku.dto.OtpDto;
import com.bca.bit.jualanku.dto.PasswordDto;
import com.bca.bit.jualanku.model.Otp;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.service.OtpService;
import com.bca.bit.jualanku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @Autowired
    OtpService otpService;

    @GetMapping("/connector")
    public String defaultAfterLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findUserByEmail(auth.getName());
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setAttempt(0);
            userService.updateUser(user);
        }
        return "redirect:/index";
    }
    @GetMapping("/index")
    public String index() {
        Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
        if (myRole.equals("BUYER")) {
            return "redirect:/buyer";
        }
        return "redirect:/seller";
    }

    @GetMapping("/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @GetMapping({"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/forgotpassword")
    public ModelAndView forgotPasswordForm(){
        ModelAndView modelAndView = new ModelAndView();
        OtpDto otpDto = new OtpDto();
        modelAndView.addObject("otpForm", otpDto);
        modelAndView.setViewName("forgot_password");
        return modelAndView;
    }

    @PostMapping("/forgotpassword")
    public ModelAndView forgotPassword(@RequestParam("email") String email){
        ModelAndView modelAndView = new ModelAndView();
        OtpDto otpDto = new OtpDto();
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        Otp otp = new Otp();
        otp.setOtp(String.format("%06d", number));
        otp.setEmail(email);
        otpService.createOtp(otp);
        modelAndView.addObject("otpForm", otpDto);
        modelAndView.setViewName("forgot_password");
        return modelAndView;
    }

    @GetMapping("/resetpassword/{id}")
    public ModelAndView resetPasswordForm(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        PasswordDto passwordDto = new PasswordDto();
        Optional<User> optionalUser = userService.findUserById(id);
        modelAndView.addObject("user", optionalUser.get());
        modelAndView.addObject("passwordForm", passwordDto);
        modelAndView.setViewName("reset_password");
        return modelAndView;
    }
}
