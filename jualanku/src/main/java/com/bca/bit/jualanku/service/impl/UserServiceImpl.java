package com.bca.bit.jualanku.service.impl;

import com.bca.bit.jualanku.model.Role;
import com.bca.bit.jualanku.model.User;
import com.bca.bit.jualanku.repository.RoleRepository;
import com.bca.bit.jualanku.repository.UserRepository;
import com.bca.bit.jualanku.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Optional<User> findUserById(Long id) {
        // Create service to find User by id from userRepository
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User createUser(User user) {
        user.setAccountNonBlocked("true");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setDateCreated(new Timestamp(System.currentTimeMillis()));
        user.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        user.setAttempt(0);
        Role userRole = roleRepository.findByRole("BUYER");
        System.out.println(userRole);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        user.setDateUpdated(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
