package com.ecommerce.service;

import com.ecommerce.model.Carrinho;
import com.ecommerce.model.Produto;
import com.ecommerce.model.ProdutoCarrinho;
import com.ecommerce.enums.ProdutoErroTipo;
import com.ecommerce.exceptions.ProdutoException;
import com.ecommerce.repository.ProdutoCarrinhoRepository;
import com.ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoCarrinhoService {

    private final ProdutoCarrinhoRepository produtoCarrinhoRepository;
    private final ProdutoRepository produtoRepository;

    public ProdutoCarrinhoService(ProdutoCarrinhoRepository produtoCarrinhoRepository,
                                  ProdutoRepository produtoRepository) {
        this.produtoCarrinhoRepository = produtoCarrinhoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoCarrinho adicionarProdutoCarrinho(Produto produto, Carrinho carrinho) {
        Produto produtoBanco = produtoRepository.findById(produto.getId())
                .orElseThrow(() -> new ProdutoException(ProdutoErroTipo.PRODUTO_NAO_ENCONTRADO));

        return carrinho.getProdutosCarrinho().stream()
                .filter(item -> item.getProduto().getId().equals(produtoBanco.getId()))
                .findFirst()
                .map(itemExistente -> {
                    itemExistente.setQuantidade(itemExistente.getQuantidade() + 1);
                    return produtoCarrinhoRepository.save(itemExistente);
                })
                .orElseGet(() -> {
                    ProdutoCarrinho novoItem = new ProdutoCarrinho();
                    novoItem.setProduto(produtoBanco);
                    novoItem.setCarrinho(carrinho);
                    novoItem.setQuantidade(1);
                    return produtoCarrinhoRepository.save(novoItem);
                });
    }
}