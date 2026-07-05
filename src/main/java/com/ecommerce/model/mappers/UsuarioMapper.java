package com.ecommerce.model.mappers;

import com.ecommerce.controller.dto.UsuarioCadastroDtoRequest;
import com.ecommerce.controller.dto.UsuarioDtoResponse;
import com.ecommerce.model.Usuario;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", uses = { CarrinhoMapper.class })
public abstract class UsuarioMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carrinho", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "senha", ignore = true)
    public abstract Usuario toEntity(UsuarioCadastroDtoRequest request);

    public abstract UsuarioDtoResponse toDto(Usuario usuario);

    @AfterMapping
    protected void criptografarSenha(UsuarioCadastroDtoRequest request, @MappingTarget Usuario usuario) {
        if (request.senha() != null) {
            usuario.setSenha(passwordEncoder.encode(request.senha()));
        }
    }
}
