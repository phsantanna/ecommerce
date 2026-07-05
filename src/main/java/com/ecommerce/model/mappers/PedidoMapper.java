package com.ecommerce.model.mappers;

import com.ecommerce.controller.dto.PedidoRequestDto;
import com.ecommerce.controller.dto.PedidoResponseDto;
import com.ecommerce.model.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = { ItemPedidoMapper.class })
public interface PedidoMapper {

    @Mapping(source = "idUsuario", target = "usuario.id")
    Pedido toEntity(PedidoRequestDto dto);

    @Mapping(source = "id", target = "idPedido")
    // 🎯 CORREÇÃO AQUI: Mapeia explicitamente o ID de dentro do objeto usuario da Entidade
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "valorTotal", target = "valorTotal")
    @Mapping(source = "itens", target = "produtosCarrinho")
    PedidoResponseDto toResponseDto(Pedido pedido);

    List<PedidoResponseDto> toResponseDtoList(List<Pedido> pedidos);
}