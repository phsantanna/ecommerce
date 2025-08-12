package com.example.demo.entities;

import com.example.demo.enums.StatusPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id", nullable = false) // Nome da coluna adicionado
    private Long id;

    @Column(nullable = false, name = "status_pedido")
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private BigDecimal valorTotal;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<ItemCarrinho> produtos = new ArrayList<>();

    public Pedido(Long id, List<ItemCarrinho> produtos, StatusPedido status, String email, BigDecimal valorTotal) {
    }

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemCarrinho> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ItemCarrinho> produtos) {
        this.produtos = produtos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}