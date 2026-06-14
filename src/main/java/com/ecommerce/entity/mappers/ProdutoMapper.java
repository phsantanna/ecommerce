package com.ecommerce.entity.mappers;

import com.ecommerce.controller.dto.ProdutoDtoRequest;
import com.ecommerce.controller.dto.ProdutoDtoResponse;
import com.ecommerce.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(source="descricaoProduto", target = "descricaoProduto")
    @Mapping(source="categoriaProduto", target = "categoriaProduto")
    @Mapping(source="nomeProduto", target = "nomeProduto")
    @Mapping(source="precoProduto", target = "precoProduto")
    @Mapping(source="qtdProduto", target = "qtdProduto")
    Produto toEntity(ProdutoDtoRequest dto);
    @Mapping(source = "id", target = "idProduto")
    @Mapping(source = "descricaoProduto", target = "descricaoProduto")
    @Mapping(source = "categoriaProduto", target = "categoriaProduto")
    @Mapping(source = "nomeProduto", target = "nomeProduto")
    @Mapping(source = "precoProduto", target = "precoProduto")
    ProdutoDtoResponse toDto(Produto entity);
}