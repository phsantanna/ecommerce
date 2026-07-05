package com.ecommerce.model.mappers;

import com.ecommerce.controller.dto.ProdutoDtoRequest;
import com.ecommerce.controller.dto.ProdutoDtoResponse;
import com.ecommerce.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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