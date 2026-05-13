package com.example.softlearning.core.entity.order.persistence;

import java.util.List;
import java.util.Optional;

import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;

public interface OrderRepository {
    public Optional<OrderJpaDTO> findById(String ref);
    public List<OrderJpaDTO> findAll();
    public List<OrderJpaDTO> findByIdClient(Integer clientId);
    public OrderJpaDTO save(OrderJpaDTO order);
    public void deleteById(String ref);
    public void delete(OrderJpaDTO entity);
    public void deleteOrderDetails(String orderRef);
}