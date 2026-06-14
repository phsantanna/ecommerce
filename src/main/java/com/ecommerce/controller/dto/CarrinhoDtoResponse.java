package com.ecommerce.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CarrinhoDtoResponse(@NotNull @Valid UUID idCarrinho,
                                  @NotEmpty @Valid List<ProdutoCarrinhoDtoResponse> produtos,
                                  @NotNull BigDecimal valorTotal) {
}
