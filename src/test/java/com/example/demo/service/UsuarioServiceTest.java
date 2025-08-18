package com.example.demo.service;

import com.example.demo.dtos.UsuarioRequestDto;
import com.example.demo.dtos.UsuarioResponseDto;
import com.example.demo.entities.Usuario;
import com.example.demo.enums.Roles;
import com.example.demo.repository.UsuarioRepository;
import org.hibernate.Interceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;
    private UsuarioRequestDto usuarioRequestDto;
    private UsuarioResponseDto usuarioResponseDto;

    @BeforeEach
    void Setup(){
        usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setEmail("abc@gmail.com");
        usuario.setRole(Roles.USER);
        usuario.setSenha("123456");

        usuarioRequestDto = new UsuarioRequestDto(usuario.getEmail(), usuario.getSenha());
    }

    @Test
    @DisplayName("Realiza o cadastro do usuário e salva no banco")
    void realizaOCadastroDoUsuarioNoBanco_DeveSalvarUsuarioComSenhaCriptografada() {

        String senhaCriptografada = "criptografada";
        when(passwordEncoder.encode("123456")).thenReturn(senhaCriptografada);


        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuarioPassadoParaSalvar = invocation.getArgument(0);
            usuarioPassadoParaSalvar.setUsuarioId(1L);
            return usuarioPassadoParaSalvar;
        });

        UsuarioResponseDto responseDto = usuarioService.CadastroUsuario(usuarioRequestDto);


        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);


        verify(usuarioRepository, times(1)).save(usuarioCaptor.capture());

        verify(passwordEncoder, times(1)).encode("123456");


        Usuario usuarioSalvo = usuarioCaptor.getValue();


        assertEquals(senhaCriptografada, usuarioSalvo.getSenha());
        assertEquals("abc@gmail.com", usuarioSalvo.getEmail());
        assertNotNull(usuarioSalvo.getCarrinho());
        assertEquals(1L, responseDto.id());
    }
}