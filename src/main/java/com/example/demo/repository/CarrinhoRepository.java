package com.example.demo.repository;

import com.example.demo.dtos.CarrinhoResponseDto;
import com.example.demo.entities.Carrinho;
import com.example.demo.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findCarrinhoByUsuarioEmail(String email);

    Optional<Carrinho> findCarrinhoByUsuario(Usuario usuario);
}