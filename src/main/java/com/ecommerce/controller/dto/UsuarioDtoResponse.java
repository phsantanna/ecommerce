package com.ecommerce.controller.dto;

import com.ecommerce.entity.Carrinho;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UsuarioDtoResponse(
        @NotNull
        UUID id,
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String cep,
        @NotBlank
        String email,
        @NotBlank
        String endereco,
        @NotBlank
        String telefone,
        @NotBlank
        String celular,
        Carrinho carrinho) {
}
