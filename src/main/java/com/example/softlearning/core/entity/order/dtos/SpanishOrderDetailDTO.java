package com.example.softlearning.core.entity.order.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpanishOrderDetailDTO {
    @JsonProperty("referencia")
    public String ref;

    @JsonProperty("cantidad")
    public int amount;

    @JsonProperty("precio")
    public double price;

    @JsonProperty("descuento")
    public double discount;

    protected  SpanishOrderDetailDTO() {
        
    }

    public SpanishOrderDetailDTO(
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
