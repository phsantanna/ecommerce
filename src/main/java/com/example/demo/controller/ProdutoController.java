package com.example.demo.controller;


import com.example.demo.dtos.ItemRequestDto;
import com.example.demo.dtos.ItemResponseDto;
import com.example.demo.service.ProdutoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Transactional
    @PostMapping("/produtos/")
    public ResponseEntity<ItemResponseDto> inserirItemNoBanco(@RequestBody @Valid ItemRequestDto itemRequestDto) {

        ItemResponseDto produtos = produtoService.inserirProdutoNoBanco(itemRequestDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(itemRequestDto.id())
                .toUri();

        return ResponseEntity.created(uri).body(produtos);
    }
}
