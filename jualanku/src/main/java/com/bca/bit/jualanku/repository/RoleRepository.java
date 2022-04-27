package com.bca.bit.jualanku.repository;

import com.bca.bit.jualanku.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}