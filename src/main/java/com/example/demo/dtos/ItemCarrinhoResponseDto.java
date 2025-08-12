package com.example.demo.dtos;

import java.math.BigDecimal;

public record ItemCarrinhoResponseDto(Long produtoId,
                                      String nomeProduto,
                                      int quantidade,
                                      BigDecimal precoUnitario) {
}
