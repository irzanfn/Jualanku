package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM S_USER WHERE EMAIL = :email", nativeQuery = true)
    Optional<User> findByEmail(@RequestAttribute("email") String email);
}