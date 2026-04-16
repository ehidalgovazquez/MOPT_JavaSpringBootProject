package com.example.softlearning.core.entity.order.persistence;

import java.util.Optional;

import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;

public interface OrderRepository {
    public Optional<OrderJpaDTO> findById(String ref);
    public OrderJpaDTO save(OrderJpaDTO order);
    public void deleteById(String ref);
}