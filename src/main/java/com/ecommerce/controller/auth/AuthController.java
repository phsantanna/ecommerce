package com.ecommerce.controller.auth;

import com.ecommerce.config.TokenService;
import com.ecommerce.controller.GenericUriController;
import com.ecommerce.controller.dto.LoginRequestDto;
import com.ecommerce.controller.dto.LoginResponseDto;
import com.ecommerce.controller.dto.UsuarioCadastroDtoRequest;
import com.ecommerce.controller.dto.UsuarioCadastroResponse;
import com.ecommerce.model.Usuario;
import com.ecommerce.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController implements GenericUriController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken usuarioEsenha = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.senha());
        Authentication authentication = authenticationManager.authenticate(usuarioEsenha);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioCadastroResponse> cadastro(@Valid @RequestBody UsuarioCadastroDtoRequest usuarioCadastroRequest) {
        var usuario = usuarioService.cadastrarUsuario(usuarioCadastroRequest);
        var uri = gerarHeaderLocation(usuario.id());
        return ResponseEntity.created(uri).build();
    }
}
