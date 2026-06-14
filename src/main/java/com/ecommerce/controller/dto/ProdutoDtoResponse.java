package com.ecommerce.controller.dto;

import com.ecommerce.enums.Categoria;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoDtoResponse(
        @NotNull @Valid UUID idProduto,
        @NotBlank String descricaoProduto,
        @NotNull Categoria categoriaProduto,
        @NotNull BigDecimal precoProduto,
        @NotBlank String nomeProduto) {}
