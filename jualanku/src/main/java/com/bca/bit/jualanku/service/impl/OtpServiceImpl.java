package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.Otp;
import com.bca.bit.jualanku.repository.OtpRepository;
import com.bca.bit.jualanku.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Override
    public Otp createOtp(Otp otp) {
        otp.setDateCreated(new Timestamp(System.currentTimeMillis()));
        otp.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return otpRepository.save(otp);
    }

    @Override
    public Optional<Otp> findOtpById(Long id) {
        Optional<Otp> otp = otpRepository.findById(id);
        if(otp.isPresent()){
            return otp;
        } else {
            throw new RuntimeException("Otp not found");
        }
    }

    @Override
    public Optional<Otp> findOtpByEmail(String email) {
        Optional<Otp> otp = otpRepository.findByEmail(email);
        if(otp.isPresent()){
            return otp;
        } else {
            throw new RuntimeException("Otp not found");
        }
    }

    @Override
    public Optional<Otp> findOtpByOtp(String otp) {
        Optional<Otp> optionalOtp = otpRepository.findByOtp(otp);
        return optionalOtp;
    }

    @Override
    public Otp updateOtp(Otp otp) {
        return otpRepository.save(otp);
    }

    @Override
    public void deleteOtp(Long id) {
        otpRepository.deleteById(id);
    }
}
