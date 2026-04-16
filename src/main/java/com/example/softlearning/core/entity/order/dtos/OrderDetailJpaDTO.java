package com.example.softlearning.core.entity.order.dtos;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetailJpaDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_ref", nullable = false)
    private OrderJpaDTO order;

    private String ref;
    private int amount;
    private double price;
    private double discount;

    protected OrderDetailJpaDTO() {}

    public OrderDetailJpaDTO(String ref, int amount, double price, double discount) {
        this.ref = ref;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
    }

    public void setOrder(OrderJpaDTO order) { this.order = order; }
    public String getRef() { return ref; }
    public int getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
}