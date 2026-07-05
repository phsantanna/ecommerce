package com.ecommerce.service;

import com.ecommerce.controller.dto.ProdutoDtoRequest;
import com.ecommerce.model.Produto;
import com.ecommerce.model.mappers.ProdutoMapper;
import com.ecommerce.enums.ProdutoErroTipo;
import com.ecommerce.exceptions.ProdutoException;
import com.ecommerce.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public void salvarProduto(ProdutoDtoRequest produtoDto) {
        Produto produto = produtoMapper.toEntity(produtoDto);
        if (produtoRepository.existsProdutoByNomeProduto((produtoDto.nomeProduto()))){
            produto.setQtdProduto(produto.getQtdProduto() + produtoDto.qtdProduto());
        } else {
            produto.setQtdProduto(produtoDto.qtdProduto());
        }
        produtoRepository.save(produto);
    }

    public Produto buscarProdutoPorId(UUID idProduto){
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoException(ProdutoErroTipo.PRODUTO_NAO_ENCONTRADO));
    }

}
