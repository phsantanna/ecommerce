package com.ecommerce.controller;

import com.ecommerce.controller.dto.UserView;
import com.ecommerce.controller.dto.UsuarioCadastroDtoRequest;
import com.ecommerce.controller.dto.UsuarioDtoResponse;
import com.ecommerce.service.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController implements GenericUriController{

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastroUsuario(@RequestBody @Validated @JsonView(UserView.RegistrationPost.class) UsuarioCadastroDtoRequest usuarioDtoReceive){
        var usuario = usuarioService.cadastrarUsuario(usuarioDtoReceive);
        var uri = gerarHeaderLocation(usuario.id());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping //CORIGIR
    public ResponseEntity<Page<UsuarioDtoResponse>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        usuarioService.findAll(pageable);
        return ResponseEntity.ok(usuarioService.findAll(pageable));
    }
}
