package com.example.demo.service;

import com.example.demo.dtos.ItemRequestDto;
import com.example.demo.dtos.ItemResponseDto;
import com.example.demo.entities.Produtos;
import com.example.demo.interfaces.ProdutoServiceImpl;
import com.example.demo.repository.ProdutosRepository;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService implements ProdutoServiceImpl {

    private final ProdutosRepository produtosRepository;

    public ProdutoService(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @Override
    public ItemResponseDto inserirProdutoNoBanco(ItemRequestDto itemRequestDto) {

        Produtos produto = new Produtos();
        produto.setCategoria(itemRequestDto.categoria());
        produto.setNome(itemRequestDto.nome());
        produto.setDescricao(itemRequestDto.descricao());
        produto.setPreco(itemRequestDto.preco());

        produtosRepository.save(produto);

        return new ItemResponseDto(produto.getProdutosId(), produto.getNome(),
                produto.getPreco(), produto.getDescricao(), produto.getCategoria());
    }
}
