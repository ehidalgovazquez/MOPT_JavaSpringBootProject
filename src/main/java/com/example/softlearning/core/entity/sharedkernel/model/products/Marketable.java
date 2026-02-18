package com.example.softlearning.core.entity.sharedkernel.model.products;

public interface Marketable {
    public double getPrice();
    public int setPrice(double price);
    public boolean isAvailable();
    public int setAvailable(boolean available);
}