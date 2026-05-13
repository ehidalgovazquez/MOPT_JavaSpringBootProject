package com.example.softlearning.infrastructure.persistence.jpa.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByToken(String token);

    @Query("SELECT t FROM TokenEntity t WHERE t.user.id = :userId AND (t.isExpired = false AND t.isRevoked = false)")
    List<TokenEntity> findAllValidTokenByUser(Long userId);
}
