package com.example.softlearning.core.entity.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetailDTO {
    @JsonProperty("ref")
    public String ref;

    @JsonProperty("amount")
    public int amount;

    @JsonProperty("price")
    public double price;

    @JsonProperty("discount")
    public double discount;

    protected  OrderDetailDTO() {
        
    }

    public OrderDetailDTO(
        String ref,
        int amount,
        double price,
        double discount
    ) {
        this.ref = ref;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
    }

    public String getRef() {
        return ref;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }
}
