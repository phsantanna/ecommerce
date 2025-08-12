package com.example.demo.controller;

import com.example.demo.dtos.ListaPedidosRequestDto;
import com.example.demo.dtos.PedidoRequestDto;
import com.example.demo.dtos.PedidoResponseDto;
import com.example.demo.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
@Tag(name = "Pedido", description = "controlador para salvar, editar e realizar pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/pedido/realizar")
    @Operation(summary = "realiza o pedido do usuario", description = "metodo que realiza o pedido")
    @ApiResponse(responseCode = "200", description = "pedido realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "pedido não realizado")
    public ResponseEntity<PedidoResponseDto> realizarPedido(@RequestBody PedidoRequestDto pedidoRequestDto) {

        PedidoResponseDto pedido = pedidoService.realizarPedido(pedidoRequestDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pedido.pedidoId())
                .toUri();

        return ResponseEntity.created(uri).body(pedido);
    }

    @GetMapping("/meus-pedidos")
    @Operation(summary = "Lista todos os pedidos do usuário autenticado", description = "Retorna uma lista com todos os pedidos feitos pelo usuário que está logado.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso.")
    public ResponseEntity<List<PedidoResponseDto>> exibirPedidos(@RequestBody ListaPedidosRequestDto emailUsuario) {

        List<PedidoResponseDto> pedidoResponseDto = pedidoService.listarTodosPedidosUsuario(emailUsuario);

        return ResponseEntity.ok(pedidoResponseDto);
    }
}
