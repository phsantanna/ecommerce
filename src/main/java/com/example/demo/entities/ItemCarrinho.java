package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_carrinho")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemCarrinhoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrinho_id", nullable = false)
    @JsonBackReference
    private Carrinho carrinho;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produtos produto;

    @Column(nullable = false)
    private int quantidade;

    public ItemCarrinho(Long itemCarrinhoId, Carrinho carrinho, Produtos produto, int quantidade) {
        this.itemCarrinhoId = itemCarrinhoId;
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemCarrinho() {
    }

    public ItemCarrinho(Long produtosId, String nome, int quantidade, BigDecimal preco) {
    }

    public Long getItemCarrinhoId() {
        return itemCarrinhoId;
    }

    public void setItemCarrinhoId(Long itemCarrinhoId) {
        this.itemCarrinhoId = itemCarrinhoId;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
