package com.ecommerce.entity.mappers;

import com.ecommerce.controller.dto.ProdutoCarrinhoDtoRequest;
import com.ecommerce.controller.dto.ProdutoCarrinhoDtoResponse;
import com.ecommerce.entity.ProdutoCarrinho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoCarrinhoMapper {

    ProdutoCarrinho toEntity(ProdutoCarrinhoDtoRequest produtoCarrinhoDtoRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "produto", target = "produto")
    @Mapping(source = "quantidade", target = "quantidade")
    @Mapping(source = "produto.id", target = "produto.idProduto")
    ProdutoCarrinhoDtoResponse toDto(ProdutoCarrinho produtoCarrinho);

    List<ProdutoCarrinhoDtoResponse> toDtoList(List<ProdutoCarrinho> produtosCarrinho);
}
