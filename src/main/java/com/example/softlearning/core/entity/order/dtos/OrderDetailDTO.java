package com.example.softlearning.core.entity.order.dtos;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
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
    @JsonProperty("book")
    private BookDTO book;

    public OrderDetailDTO() {}

    public OrderDetailDTO(int bookId, int amount, double price, double discount) {
        this(bookId, amount, price, discount, null);
    }

    public OrderDetailDTO(int bookId, int amount, double price, double discount, BookDTO book) {
        this.bookId = bookId;
        this.amount = amount;
        this.price = price;
        this.discount = discount;
        this.book = book;
    }

    public int getBookId() { return bookId; }
    public int getAmount() { return amount; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }
    public BookDTO getBook() { return book; }
}