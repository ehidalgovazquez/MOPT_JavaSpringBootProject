package com.example.softlearning.core.entity.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetailDTO {
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("price")
    private double price;
    @JsonProperty("discount")
    private double discount;

    public OrderDetailDTO() {}

    public OrderDetailDTO(String ref, int amount, double price, double discount) {
        this.ref = ref;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
    }

    public String getRef() { return ref; }
    public int getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
}