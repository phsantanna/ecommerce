package com.ecommerce.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(@NotEmpty(message = "Email obrigatório") String email,
                              @NotEmpty(message = "Senha obrigatória")@NotNull String senha) {
}
