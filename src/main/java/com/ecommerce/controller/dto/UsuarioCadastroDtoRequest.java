package com.ecommerce.controller.dto;


import com.ecommerce.validation.EmailConstraint;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDtoRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 100, message = "Nome fora do tamanho padrão")
        @JsonView(UserView.RegistrationPost.class)
        String nome,

        @NotBlank(message = "Email é obrigatório", groups = UserView.RegistrationPost.class)
        @Email(message = "Formato de email inválido")
        @EmailConstraint(groups = UserView.RegistrationPost.class)
        @JsonView(UserView.RegistrationPost.class)
        String email,

        @NotBlank(message = "Senha é obrigatória")
        @JsonView(UserView.RegistrationPost.class)
        String senha,

        @NotBlank(message = "CPF é obrigatório")
        @Size(min = 11, max = 11, message = "O CPF deve conter exatamente 11 caracteres.")
        @JsonView(UserView.RegistrationPost.class)
        String cpf,

        @NotBlank(message = "CEP é obrigatório")
        @JsonView(UserView.RegistrationPost.class)
        String cep,

        @NotBlank(message = "Endereço é obrigatório")
        @JsonView(UserView.RegistrationPost.class)
        String endereco,

        @JsonView(UserView.RegistrationPost.class)
        String telefone,

        @NotBlank(message = "Celular é obrigatório")
        @JsonView(UserView.RegistrationPost.class)
        String celular) {
}
