package com.bca.bit.jualanku.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "S_USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "PHONE", nullable = false, length = 20)
    private String phone;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Column(name = "ADDRESS", nullable = false, length = 225)
    private String address;

    @Column(name = "DATE_CREATED", nullable = false)
    private Timestamp dateCreated;

    @Column(name = "DATE_UPDATED", nullable = false)
    private Timestamp dateUpdated;

    @Column(name= "ACCOUNT_NON_BLOCKED", nullable = false)
    private String accountNonBlocked;

    @Column(name= "ATTEMPT", nullable = false)
    private Integer attempt;

    @ManyToMany(cascade=CascadeType.MERGE)
    @JoinTable(name = "T_USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}