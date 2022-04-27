package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.model.Wallet;
import com.bca.bit.jualanku.repository.WalletRepository;
import com.bca.bit.jualanku.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;


    @Override
    public Wallet createWallet(Wallet wallet) {
        wallet.setTotalBalance(0L);
        wallet.setDateCreated(new Timestamp(System.currentTimeMillis()));
        wallet.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return walletRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> findWalletById(Long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet;
        } else {
            throw new RuntimeException("Wallet not found");
        }
    }

    @Override
    public Optional<Wallet> findWalletByBuyer(User user) {
        Optional<Wallet> wallet = walletRepository.findByBuyer(user);
        if(wallet.isPresent()){
            return wallet;
        } else {
            throw new RuntimeException("Wallet not found");
        }
    }

    @Override
    public Wallet updateWallet(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }


}
