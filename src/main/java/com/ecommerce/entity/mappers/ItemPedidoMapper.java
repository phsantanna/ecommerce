package com.ecommerce.entity.mappers;

import com.ecommerce.controller.dto.ItemPedidoDtoResponse;
import com.ecommerce.controller.dto.ProdutoDtoResponse;
import com.ecommerce.entity.ItemPedido;
import com.ecommerce.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    @Mapping(source = "id", target = "idItem")
    @Mapping(source = "precoUnitario", target = "precoUnitario")
    @Mapping(source = "quantidade", target = "quantidade")
    @Mapping(source = "produto", target = "produto")
    ItemPedidoDtoResponse toDto(ItemPedido itemPedido);

    @Mapping(source = "id", target = "idProduto")
    ProdutoDtoResponse toProdutoDto(Produto produto);
}