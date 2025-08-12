package com.example.demo.dtos;

import com.example.demo.enums.Roles;

public record UsuarioResponseDto(Long id, String email, Roles role) {
}
