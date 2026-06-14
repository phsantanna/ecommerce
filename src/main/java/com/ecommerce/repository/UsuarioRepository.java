package com.ecommerce.repository;

import com.ecommerce.controller.dto.LoginRequestDto;
import com.ecommerce.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsUsuarioByEmail(String email);

    boolean existsUsuarioByCelular(String celular);

   Optional<UserDetails> findByEmail(String email);

    Optional<Usuario> findUsuarioById(UUID id);
}