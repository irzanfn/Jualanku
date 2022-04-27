package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByBuyer(User user);
}