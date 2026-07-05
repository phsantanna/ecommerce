package com.ecommerce.model;

import com.ecommerce.enums.Categoria;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "idProduto", nullable = false, unique = true)
    private UUID id;

    @Column(name = "nomeProduto", nullable = false)
    private String nomeProduto;

    @Column(name = "descricaoProduto", nullable = false)
    private String descricaoProduto;

    @Column(name = "categoriaProduto", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoriaProduto;

    @Column(name = "precoProduto", nullable = false)
    private BigDecimal precoProduto;
    
    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private List<ProdutoCarrinho> produtosCarrinho = new ArrayList<>();

    @Column(name = "quantidadeProduto", nullable = false)
    private Integer qtdProduto;

}