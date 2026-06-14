package com.ecommerce.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioCadastroResponse (@NotBlank @Email String email, @NotBlank String nome) {
}
