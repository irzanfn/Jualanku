package com.bca.bit.jualanku.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "T_WALLET")
@Data
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WALLET_ID", nullable = false, insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUYER_ID", nullable = false)
    private User buyer;

//    @Column(name = "CC_NUMBER", nullable = false)
//    private Long ccNumber;
//
//    @Column(name = "EXP_DATE", nullable = false)
//    private LocalDate expDate;

    @Column(name = "TOTAL_BALANCE", nullable = false)
    private Long totalBalance;

    @Column(name = "DATE_CREATED", nullable = false)
    private Timestamp dateCreated;

    @Column(name = "DATE_UPDATED", nullable = false)
    private Timestamp dateUpdated;

}