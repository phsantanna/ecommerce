package com.ecommerce.service;

import com.ecommerce.controller.dto.UsuarioCadastroDtoRequest;
import com.ecommerce.controller.dto.UsuarioDtoResponse;
import com.ecommerce.entity.Carrinho;
import com.ecommerce.entity.Usuario;
import com.ecommerce.entity.mappers.UsuarioMapper;
import com.ecommerce.enums.UsuarioErroTipo;
import com.ecommerce.exceptions.UsuarioException;
import com.ecommerce.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension; // Mudança aqui

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {


    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private CarrinhoService carrinhoService;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioCadastroDtoRequest criarRequest() {
        return new UsuarioCadastroDtoRequest(
                "paulo", "abc@gmail.com", "12345",
                "177384995349", "2111344", "rua tal",
                "123456544", "124512521"
        );
    }

    private Usuario criarUsuarioInstanciado() {
        Usuario usuario = new Usuario();
        usuario.setNome("paulo");
        usuario.setEmail("abc@gmail.com");
        usuario.setCelular("124512521");
        return usuario;
    }

    private UsuarioDtoResponse criarResponse(Usuario usuario) {
        return new UsuarioDtoResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getCep(),
                usuario.getEmail(),
                usuario.getEndereco(),
                usuario.getTelefone(),
                usuario.getCelular(),
                usuario.getCarrinho()
        );
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso quando dados forem únicos")
    void cadastrarUsuarioComSucesso() {

        UsuarioCadastroDtoRequest request = criarRequest();
        Usuario usuario = criarUsuarioInstanciado();
        Carrinho carrinhoMocked = new Carrinho();
        UsuarioDtoResponse responseEsperado = criarResponse(usuario);

        when(usuarioRepository.existsUsuarioByEmail(request.email())).thenReturn(false);
        when(usuarioRepository.existsUsuarioByCelular(request.celular())).thenReturn(false);

        when(usuarioMapper.toEntity(request)).thenReturn(usuario);
        when(carrinhoService.criarCarrinho(usuario)).thenReturn(carrinhoMocked);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(responseEsperado);

        UsuarioDtoResponse resultado = usuarioService.cadastrarUsuario(request);

        assertThat(resultado).isNotNull();

        verify(usuarioMapper, times(1)).toEntity(request);
        verify(carrinhoService, times(1)).criarCarrinho(usuario);
        verify(usuarioRepository, times(1)).save(usuario);
        verify(usuarioMapper, times(1)).toDto(usuario);
    }
    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar usuário com email já esteja cadastrado")
    void deveRetornarExcecaoCasoEmailJaEstejaCadastrado(){
        UsuarioCadastroDtoRequest request = criarRequest();
        when(usuarioRepository.existsUsuarioByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.cadastrarUsuario(request))
                .isInstanceOf(UsuarioException.class)
                .hasMessage(UsuarioErroTipo.EMAIL_JA_CADASTRADO.getMensagem());

        verify(usuarioRepository,times(1)).existsUsuarioByEmail(request.email());
        verify(usuarioRepository,never()).existsUsuarioByCelular(request.celular());
        verify(usuarioMapper, never()).toEntity(any());
        verify(carrinhoService, never()).criarCarrinho(any());
        verify(usuarioRepository, never()).save(any());
        verify(usuarioMapper, never()).toDto(any());

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar usuário com celular já esteja cadastrado")
    void deveRetornarExcecaoCasoCelularJaEstejaCadastrado(){
        UsuarioCadastroDtoRequest request = criarRequest();

        when(usuarioRepository.existsUsuarioByEmail(request.email())).thenReturn(false);
        when(usuarioRepository.existsUsuarioByCelular(request.celular())).thenReturn(true);

        assertThatThrownBy(() -> usuarioService.cadastrarUsuario(request))
                .isInstanceOf(UsuarioException.class)
                .hasMessage(UsuarioErroTipo.CELULAR_JA_CADASTRADO.getMensagem());

        verify(usuarioRepository,times(1)).existsUsuarioByEmail(request.email());
        verify(usuarioRepository, times(1)).existsUsuarioByCelular(request.celular());
        verify(usuarioMapper, never()).toEntity(any());
        verify(carrinhoService, never()).criarCarrinho(any());
        verify(usuarioRepository, never()).save(any());

    }

}