package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.model.Wallet;

import java.util.Optional;

public interface WalletService {

    // create wallet
    Wallet createWallet(Wallet wallet);

    // find wallet by id
    Optional<Wallet> findWalletById(Long id);

    // find wallet by buyer id
    Optional<Wallet> findWalletByBuyer(User user);

    // update wallet
    Wallet updateWallet(Wallet wallet);

    // delete wallet
    void deleteWallet(Long id);
}
