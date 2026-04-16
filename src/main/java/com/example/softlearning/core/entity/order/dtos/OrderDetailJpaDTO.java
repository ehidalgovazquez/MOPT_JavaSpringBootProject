package com.example.softlearning.core.entity.order.dtos;

import com.example.softlearning.core.entity.book.dtos.BookDTO;

import jakarta.persistence.Column;
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

    @Column(name = "book_id", nullable = false)
    private int ref;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private BookDTO book;
    private int amount;
    private double price;
    private double discount;

    protected OrderDetailJpaDTO() {}

    public OrderDetailJpaDTO(int bookId, int amount, double price, double discount) {
        this.ref = bookId;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
    }

    public int getRef() { return ref; }
    public int getBookId() { return ref; }
    public int getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
    public OrderJpaDTO getOrder() { return order; }
    public BookDTO getBook() { return book; }
    
    public void setOrder(OrderJpaDTO order) { this.order = order; }
    public void setBook(BookDTO book) { this.book = book; }
}