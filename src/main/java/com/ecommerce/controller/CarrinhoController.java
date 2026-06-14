package com.ecommerce.controller;

import com.ecommerce.controller.dto.CarrinhoDtoRequest;
import com.ecommerce.controller.dto.CarrinhoDtoResponse;
import com.ecommerce.controller.dto.UsuarioDtoRequest;
import com.ecommerce.service.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @GetMapping
    public ResponseEntity<CarrinhoDtoResponse> getCarrinho(@RequestBody @Valid UsuarioDtoRequest usuario){
        CarrinhoDtoResponse carrinho = carrinhoService.obterCarrinhoPorUsuario(usuario);
        return ResponseEntity.ok(carrinho);
    }

    @PostMapping
    public ResponseEntity<CarrinhoDtoResponse> inserirNoCarrinho(@RequestBody @Valid CarrinhoDtoRequest carrinhoDtoRequest){
        CarrinhoDtoResponse carrinho = carrinhoService.inserirNoCarrinho(carrinhoDtoRequest);
        return ResponseEntity.ok(carrinho);
    }
}
