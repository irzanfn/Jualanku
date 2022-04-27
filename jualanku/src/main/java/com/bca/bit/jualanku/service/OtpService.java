package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.Otp;

import java.util.Optional;

public interface OtpService {
    // create otp
    Otp createOtp(Otp otp);

    // find otp by id
    Optional<Otp> findOtpById(Long id);

    // find otp by email
    Optional<Otp> findOtpByEmail(String email);

    Optional<Otp> findOtpByOtp(String otp);


    // update otp
    Otp updateOtp(Otp otp);

    // delete otp
    void deleteOtp(Long id);
}
