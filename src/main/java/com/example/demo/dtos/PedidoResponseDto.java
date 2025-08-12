package com.example.demo.dtos;

import com.example.demo.entities.ItemCarrinho;
import com.example.demo.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDto(
        Long pedidoId,
        Long usuarioId,         // <-- Correto: Apenas o ID (dado primitivo)
        String emailUsuario,    // <-- Correto: Apenas o email (dado primitivo)
        StatusPedido status,
        BigDecimal valorTotal
) {

}