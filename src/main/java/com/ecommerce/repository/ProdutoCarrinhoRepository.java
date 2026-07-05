package com.ecommerce.repository;

import com.ecommerce.model.ProdutoCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoCarrinhoRepository extends JpaRepository<ProdutoCarrinho, UUID> {
}