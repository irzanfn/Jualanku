package com.bca.bit.jualanku.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "T_PRODUCT")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", nullable = false, insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SELLER_ID", nullable = false)
    private User seller;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "IMG", nullable = false)
    private String img;

    @Column(name = "PRICE", nullable = false)
    private Long price;

    @Column(name = "STOCK_AVAILABLE", nullable = false)
    private Integer stockAvailable;

    @Column(name = "STOCK_ORDERED", nullable = false)
    private Integer stockOrdered;

    @Column(name = "STOCK_SOLD", nullable = false)
    private Integer stockSold;

    @Column(name = "DATE_CREATED", nullable = false)
    private Timestamp dateCreated;

    @Column(name = "DATE_UPDATED", nullable = false)
    private Timestamp dateUpdated;

    @Column(name = "IS_DELETED", nullable = false, length = 5)
    private String isDeleted;

}