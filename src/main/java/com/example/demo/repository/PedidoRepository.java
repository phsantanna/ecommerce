package com.example.demo.repository;

import com.example.demo.entities.Pedido;
import com.example.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findPedidoById(Long id);

    List<Pedido> findAllById(Long id);

}