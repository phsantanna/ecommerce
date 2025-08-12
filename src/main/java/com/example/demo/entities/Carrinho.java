package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "Carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carrinho_id", nullable = false)
    private Long carrinhoId;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", unique = true)
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();


    private BigDecimal valorTotal;

    public Carrinho() {
    }

    public Carrinho(Long carrinhoId, Usuario usuario, List<ItemCarrinho> itens, BigDecimal valorTotal) {
        this.carrinhoId = carrinhoId;
        this.usuario = usuario;
        this.itens = itens;
        this.valorTotal = valorTotal;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getCarrinhoId() {
        return carrinhoId;
    }

    public void setCarrinhoId(Long carrinhoId) {
        this.carrinhoId = carrinhoId;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrinho carrinho = (Carrinho) o;
        return Objects.equals(carrinhoId, carrinho.carrinhoId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(carrinhoId);
    }
}