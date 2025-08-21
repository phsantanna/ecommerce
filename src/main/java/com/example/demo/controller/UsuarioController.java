package com.example.demo.controller;

import com.example.demo.dtos.UsuarioRequestDto;
import com.example.demo.dtos.UsuarioResponseDto;
import com.example.demo.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping
@Tag(name = "Usuario", description = "Controlador para realizar operações com usuario")
public class UsuarioController {

    public final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    @ApiResponse(responseCode = "200", description = "Cadastra um novo usuario")
    public ResponseEntity<UsuarioResponseDto> novoUsuario(@RequestBody UsuarioRequestDto usuarioRequestDto) {

        UsuarioResponseDto usuarioResponseDto = usuarioService.CadastroUsuario(usuarioRequestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioResponseDto.id())
                .toUri();
        return ResponseEntity.created(uri).body(usuarioResponseDto);
    }
}
