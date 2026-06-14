package com.ecommerce.controller;

import com.ecommerce.controller.dto.PedidoRequestDto;
import com.ecommerce.controller.dto.PedidoResponseDto;
import com.ecommerce.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("pedido")
public class PedidoController implements GenericUriController{

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PedidoResponseDto>> listarPedidos(@PathVariable UUID id) { // 🎯 Recebe UUID

        List<PedidoResponseDto> listaPedidosUsuario = pedidoService.listarPedidosUsuario(id);

        return ResponseEntity.ok(listaPedidosUsuario);
    }

    @GetMapping("/finalizar")
    public ResponseEntity<PedidoResponseDto> finalizarPedido(@RequestBody PedidoRequestDto idUsuario) {
        var pedido = pedidoService.finalizarPedido(idUsuario);
        return ResponseEntity.status(201).body(pedido);
    }
}
