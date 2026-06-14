package com.ecommerce.controller.dto;

import com.ecommerce.enums.StatusPedido;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDto(
        UUID idPedido,
        UUID idUsuario,
        StatusPedido status,
        BigDecimal valorTotal,
        List<ItemPedidoDtoResponse> produtosCarrinho
) {}
