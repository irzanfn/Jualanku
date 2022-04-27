package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.Cart;
import com.bca.bit.jualanku.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    @Query(value = "SELECT * FROM T_OTP WHERE EMAIL = :email ORDER BY OTP_ID DESC fetch first 1 row only", nativeQuery = true)
    Optional<Otp> findByEmail(@Param("email") String email);

    Optional<Otp> findByOtp(String otp);
}