package com.ecommerce.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoDtoResponse(
        UUID idItem,
        ProdutoDtoResponse produto,
        Integer quantidade,
        BigDecimal precoUnitario
) {}