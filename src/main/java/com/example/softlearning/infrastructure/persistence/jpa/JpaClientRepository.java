package com.example.softlearning.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.softlearning.core.entity.client.dtos.ClientDTO;
import com.example.softlearning.core.entity.client.persistence.ClientRepository;

import jakarta.transaction.Transactional;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientDTO, Integer>, ClientRepository {
    public Optional<ClientDTO> findById(int id);

    public List<ClientDTO> findByEmail(String email);
 
    @Query(value="SELECT b FROM ClientDTO b WHERE b.email LIKE %:email%")
    public List<ClientDTO> findByPartialEmail(String email);

    @Query(value="SELECT count(*) FROM ClientDTO b WHERE b.email LIKE %:email%")
    public Integer countByPartialEmail(String email);

    @Transactional
    @Override
    public ClientDTO save(ClientDTO Client);
    public void deleteById(int id);
    
}
