package com.ecommerce.service;

import com.ecommerce.controller.dto.UsuarioCadastroDtoRequest;
import com.ecommerce.controller.dto.UsuarioDtoResponse;
import com.ecommerce.model.Usuario;
import com.ecommerce.model.mappers.UsuarioMapper;
import com.ecommerce.enums.UsuarioErroTipo;
import com.ecommerce.exceptions.UsuarioException;
import com.ecommerce.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final CarrinhoService carrinhoService;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, CarrinhoService carrinhoService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.carrinhoService = carrinhoService;
    }

    public UsuarioDtoResponse cadastrarUsuario(UsuarioCadastroDtoRequest usuarioCadastroDtoRequest) {
        if (usuarioRepository.existsUsuarioByEmail(usuarioCadastroDtoRequest.email())){
            throw new UsuarioException(UsuarioErroTipo.EMAIL_JA_CADASTRADO);
        } else if (usuarioRepository.existsUsuarioByCelular(usuarioCadastroDtoRequest.celular())) {
            throw new UsuarioException(UsuarioErroTipo.CELULAR_JA_CADASTRADO);
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioCadastroDtoRequest);
        usuario.setCarrinho(carrinhoService.criarCarrinho(usuario));
        usuarioRepository.save(usuario);
        return usuarioMapper.toDto(usuario);
    }

    public Page<UsuarioDtoResponse> findAll(Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(usuarioMapper::toDto);
    }
}
