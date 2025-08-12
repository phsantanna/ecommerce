package com.example.demo.dtos;

import java.math.BigDecimal;

public record ItemResponseDto(Long id, String nome, BigDecimal preco, String descricao, String categoria) {
}
