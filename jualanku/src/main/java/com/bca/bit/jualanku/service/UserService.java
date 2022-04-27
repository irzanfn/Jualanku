package com.bca.bit.jualanku.service;

import com.bca.bit.jualanku.model.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findUserById(Long id);
    public Optional<User> findUserByEmail(String email);
    public User createUser(User User);
    public User updateUser(User User);
    public void deleteUser(Long id);
}
