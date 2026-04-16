package com.example.softlearning.core.entity.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetailDTO {
    @JsonProperty("bookId")
    private int bookId;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("price")
    private double price;
    @JsonProperty("discount")
    private double discount;

    public OrderDetailDTO() {}

    public OrderDetailDTO(int bookId, int amount, double price, double discount) {
        this.bookId = bookId;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
    }

    public int getBookId() { return bookId; }
    public int getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
}