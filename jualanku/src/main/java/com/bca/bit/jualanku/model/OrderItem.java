package com.bca.bit.jualanku.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "T_ORDER_ITEM")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SELLER_ID", nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BUYER_ID", nullable = false)
    private User buyer;

    @Column(name = "PRODUCT_QTY", nullable = false)
    private Integer productQty;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    @Column(name = "DATE_CREATED", nullable = false)
    private Timestamp dateCreated;

    @Column(name = "DATE_UPDATED", nullable = false)
    private Timestamp dateUpdated;

    public OrderItem(Order order, Product product, User seller, User buyer, Integer productQty, String status, Timestamp dateCreated, Timestamp dateUpdated) {
        this.order = order;
        this.product = product;
        this.seller = seller;
        this.buyer = buyer;
        this.productQty = productQty;
        this.status = status;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public OrderItem() {

    }
}