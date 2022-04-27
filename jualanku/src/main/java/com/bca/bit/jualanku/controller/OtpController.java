package com.bca.bit.jualanku.controller;

import com.bca.bit.jualanku.dto.OtpDto;
import com.bca.bit.jualanku.model.CartItem;
import com.bca.bit.jualanku.model.Otp;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.service.OtpService;
import com.bca.bit.jualanku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class OtpController {

    @Autowired
    OtpService otpService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/otp")
    @ResponseBody
    public String findOtpByEmail(@RequestParam("email") String email){
        Otp otp = new Otp();
        Optional<Otp> optionalOtp = otpService.findOtpByEmail(email);
        if(optionalOtp.isPresent()){
            otp = optionalOtp.get();
        }
        String otpValue = otp.getOtp();
        return otpValue;
    }

    @PostMapping(value = "/otp")
    public String checkOtp(@ModelAttribute("otpForm") OtpDto otpDto){
        String otp = otpDto.getFirst()+otpDto.getSecond()+otpDto.getThird()+otpDto.getFourth()+otpDto.getFifth()+otpDto.getSixth();
        System.out.println("=============");
        System.out.println(otp);
        System.out.println("=============");
        Optional<Otp> optionalOtp = otpService.findOtpByOtp(otp);
        if(optionalOtp.isPresent()){
            Optional<User> optionalUser = userService.findUserByEmail(optionalOtp.get().getEmail());
            if(optionalUser.isPresent()){
                System.out.println("Masuk");
                return "redirect:/resetpassword/" + optionalUser.get().getId();
            }
        }
        return "redirect:/forgotpassword";
    }
}
