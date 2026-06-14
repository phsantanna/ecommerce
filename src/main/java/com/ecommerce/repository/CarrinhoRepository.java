package com.ecommerce.repository;

import com.ecommerce.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID> {
    boolean existsByUsuarioId(UUID id);

    Optional<Carrinho> findCarrinhoByUsuarioId(UUID usuarioId);
}