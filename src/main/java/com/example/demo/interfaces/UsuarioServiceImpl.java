package com.example.demo.interfaces;

import com.example.demo.dtos.UsuarioRequestDto;
import com.example.demo.dtos.UsuarioResponseDto;
import com.example.demo.entities.Usuario;

public interface UsuarioServiceImpl {

    UsuarioResponseDto CadastroUsuario(UsuarioRequestDto usuarioDto);
}
