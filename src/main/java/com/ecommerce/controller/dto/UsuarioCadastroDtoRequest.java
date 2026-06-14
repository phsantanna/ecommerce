package com.ecommerce.controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDtoRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100, message = "Nome fora do tamanho padrão")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Formato de email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 caracteres.")
        String cpf,

        @NotBlank(message = "CEP é obrigatório")
        String cep,

        @NotBlank(message = "Endereço é obrigatório")
        String endereco,

        String telefone, // Pode ser opcional

        @NotBlank(message = "Celular é obrigatório")
        String celular) {
}
