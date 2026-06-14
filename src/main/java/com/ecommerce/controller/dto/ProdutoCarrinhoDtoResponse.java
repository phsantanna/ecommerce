package com.ecommerce.controller.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProdutoCarrinhoDtoResponse(@NotNull UUID id,
                                         ProdutoDtoResponse produto,
                                         @NotNull Integer quantidade) {
}
