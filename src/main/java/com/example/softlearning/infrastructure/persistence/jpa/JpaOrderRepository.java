package com.example.softlearning.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.softlearning.core.entity.order.dtos.OrderJpaDTO;
import com.example.softlearning.core.entity.order.persistence.OrderRepository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderJpaDTO, String>, OrderRepository {

    @Override
    public Optional<OrderJpaDTO> findById(String ref);

    @Override
    public List<OrderJpaDTO> findAll();

    @Override
    public List<OrderJpaDTO> findByIdClient(Integer idClient);

    @Override
    public OrderJpaDTO save(OrderJpaDTO order);
    
    @Override
    @Transactional
    @Modifying
    @Query("DELETE FROM OrderDetailJpaDTO od WHERE od.order.ref = ?1")
    public void deleteOrderDetails(String ref);
    
    @Override
    @Transactional
    @Modifying
    @Query("DELETE FROM OrderJpaDTO o WHERE o.ref = ?1")
    public void deleteById(String ref);
}