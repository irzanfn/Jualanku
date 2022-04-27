package com.bca.bit.jualanku.dto;

import com.bca.bit.jualanku.model.OrderItem;

import java.util.List;

public class OrderItemDto {
    public List<OrderItem> orderItems;

    public OrderItemDto() {

    }

    public OrderItemDto(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
