package com.example.softlearning.infrastruture.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
import com.example.softlearning.core.entity.order.persistence.OrderRepository;

import jakarta.transaction.Transactional;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderJpaDTO, String>, OrderRepository {

    @Override
    public Optional<OrderJpaDTO> findById(String ref);

    @Transactional
    @Override
    public OrderJpaDTO save(OrderJpaDTO order);

    @Override
    public void deleteById(String ref);
}