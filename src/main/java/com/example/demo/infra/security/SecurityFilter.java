package com.example.demo.infra.security;

import com.example.demo.entities.Usuario;
import com.example.demo.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recovertoken(request);
        if (token != null) {
             var subject = tokenService.validateToken(token);
             Optional<Usuario> usuario = usuarioRepository.findUsuarioByEmail(subject);
             var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.get().getAuthorities());
             SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);
    }

    public String recovertoken(HttpServletRequest request) {

        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replaceAll("Bearer ", "");
    }

}
