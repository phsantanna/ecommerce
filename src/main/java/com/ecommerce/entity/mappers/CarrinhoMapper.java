package com.ecommerce.entity.mappers;

import com.ecommerce.entity.Carrinho;
import com.ecommerce.controller.dto.CarrinhoDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProdutoCarrinhoMapper.class})
public interface CarrinhoMapper {

    @Mapping(source = "id", target = "idCarrinho")

    @Mapping(source = "produtosCarrinho", target = "produtos")
    @Mapping(source = "valorTotal", target = "valorTotal")
    CarrinhoDtoResponse toDto(Carrinho carrinho);
    Carrinho toEntity(CarrinhoDtoResponse carrinhoDtoResponse);



}