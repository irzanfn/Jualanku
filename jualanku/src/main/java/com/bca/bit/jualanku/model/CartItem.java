package com.bca.bit.jualanku.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_CART_ITEM")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_ITEM_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CART_ID", nullable = false)
    private Cart cart;

    @Column(name = "PRODUCT_QTY", nullable = false)
    private Integer productQty;

    @Column(name = "DATE_CREATED", nullable = false)
    private Timestamp dateCreated;

    @Column(name = "DATE_UPDATED", nullable = false)
    private Timestamp dateUpdated;

}