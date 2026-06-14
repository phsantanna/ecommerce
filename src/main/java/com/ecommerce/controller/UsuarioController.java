package com.ecommerce.controller;

import com.ecommerce.controller.dto.UsuarioCadastroDtoRequest;
import com.ecommerce.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioController implements GenericUriController{

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastroUsuario(@RequestBody @Valid UsuarioCadastroDtoRequest usuarioDtoReceive){
        var usuario = usuarioService.cadastrarUsuario(usuarioDtoReceive);
        var uri = gerarHeaderLocation(usuario.id());
        return ResponseEntity.created(uri).build();
    }

}
