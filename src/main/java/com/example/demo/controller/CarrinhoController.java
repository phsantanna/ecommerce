package com.example.demo.controller;


import com.example.demo.dtos.CarrinhoResponseDto;
import com.example.demo.dtos.EmailRequestDto;
import com.example.demo.dtos.InserirItemRequestDto;
import com.example.demo.service.CarrinhoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CarrinhoController {

    public final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @Transactional
    @GetMapping("/carrinho/")
    public ResponseEntity<CarrinhoResponseDto> exibirCarrinho(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        CarrinhoResponseDto carrinhoResponseDto = carrinhoService.verCarrinhoUsuario(emailRequestDto);
        return ResponseEntity.ok(carrinhoResponseDto);
    }

    @Transactional
    @PostMapping("/carrinho/")
    public ResponseEntity<CarrinhoResponseDto> inserirItemNoCarrinho(@RequestBody @Valid InserirItemRequestDto inserirItemRequestDto) {

        CarrinhoResponseDto carrinhoResponseDto = carrinhoService.inserirItemNoCarrinho(inserirItemRequestDto);

        return ResponseEntity.ok(carrinhoResponseDto);
    }
}
