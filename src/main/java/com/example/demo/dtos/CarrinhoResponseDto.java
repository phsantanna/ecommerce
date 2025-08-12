package com.example.demo.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CarrinhoResponseDto(Long id, List<ItemCarrinhoResponseDto> itemCarrinhoList, BigDecimal valorTotal) {
}
