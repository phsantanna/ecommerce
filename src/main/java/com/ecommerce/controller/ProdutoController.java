package com.ecommerce.controller;

import com.ecommerce.controller.dto.ProdutoDtoRequest;
import com.ecommerce.controller.dto.ProdutoDtoResponse;
import com.ecommerce.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDtoResponse> salvarProduto(@RequestBody ProdutoDtoRequest produtoDtoRequest) {
        produtoService.salvarProduto(produtoDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
