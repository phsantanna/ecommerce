package com.example.demo.service;

import com.example.demo.dtos.UsuarioRequestDto;
import com.example.demo.dtos.UsuarioResponseDto;
import com.example.demo.entities.Carrinho;
import com.example.demo.entities.Usuario;
import com.example.demo.enums.Roles;
import com.example.demo.interfaces.UsuarioServiceImpl;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UsuarioServiceImpl {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioResponseDto CadastroUsuario(UsuarioRequestDto usuarioDto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuarioDto.email());
        String senhaCriptografada = passwordEncoder.encode(usuarioDto.senha());

        novoUsuario.setSenha(senhaCriptografada);

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(novoUsuario);

        novoUsuario.setCarrinho(carrinho);
        novoUsuario.setRole(Roles.USER);
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return new UsuarioResponseDto(usuarioSalvo.getUsuarioId(), usuarioSalvo.getEmail(), novoUsuario.getRole());
    }
}
