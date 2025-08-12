package com.example.demo.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Produtos")
public class Produtos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id", nullable = false) // Nome da coluna ajustado para clareza
    private Long produtosId;
    private String nome;
    private BigDecimal preco;
    private String descricao;
    private String categoria;

    public Produtos() {
    }

    public Produtos(Long produtosId, String nome, BigDecimal preco, String descricao, String categoria) {
        this.produtosId = produtosId;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public Long getProdutosId() {
        return produtosId;
    }

    public void setProdutosId(Long produtosId) {
        this.produtosId = produtosId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}