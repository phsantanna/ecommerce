package com.ecommerce.controller.dto;

import com.ecommerce.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoDtoRequest(@NotBlank String descricaoProduto,
                                @NotNull Categoria categoriaProduto,
                                @NotNull BigDecimal precoProduto,
                                @NotBlank String nomeProduto,
                                @NotNull Integer qtdProduto) {
}
