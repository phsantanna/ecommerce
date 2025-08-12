package com.example.demo.interfaces;

import com.example.demo.dtos.ListaPedidosRequestDto;
import com.example.demo.dtos.PedidoRequestDto;
import com.example.demo.dtos.PedidoResponseDto;
import com.example.demo.entities.Pedido;

import java.util.List;

public interface PedidoServiceImpl {

    public PedidoResponseDto realizarPedido(PedidoRequestDto pedidoRequestDto);

    List<PedidoResponseDto> listarTodosPedidosUsuario(ListaPedidosRequestDto emailUsuario);
}
