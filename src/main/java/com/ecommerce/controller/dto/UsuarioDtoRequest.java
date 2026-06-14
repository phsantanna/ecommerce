package com.ecommerce.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioDtoRequest(@NotNull UUID id) {
}
