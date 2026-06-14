package com.ecommerce.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarrinhoDtoRequest(@NotNull UUID idUsuario,
                                 @NotNull UUID idProduto) {
}
