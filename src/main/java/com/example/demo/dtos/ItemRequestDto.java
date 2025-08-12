package com.example.demo.dtos;

import java.math.BigDecimal;

public record ItemRequestDto(Long id, String nome, BigDecimal preco, String descricao, String categoria) {
}
