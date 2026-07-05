package com.ecommerce.repository;

import com.ecommerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    // 🎯 O Spring Data entende isso como: busque na propriedade 'usuario' o campo 'id'
    Optional <List<Pedido>> findAllByUsuarioId(UUID idUsuario);
}