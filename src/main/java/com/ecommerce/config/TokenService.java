package com.ecommerce.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ecommerce.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {

        return JWT.create()
                .withIssuer("auth")
                .withSubject(usuario.getEmail())
                .withExpiresAt(getExpirationTime())
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("auth")
                .build()
                .verify(token)
                .getSubject();
    }


    public Instant getExpirationTime() {
        return Instant.now().plusSeconds(3600);
    }
}
