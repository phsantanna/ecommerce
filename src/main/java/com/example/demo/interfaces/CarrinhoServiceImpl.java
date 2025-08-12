package com.example.demo.interfaces;

import com.example.demo.dtos.CarrinhoResponseDto;
import com.example.demo.dtos.EmailRequestDto;
import com.example.demo.dtos.InserirItemRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface CarrinhoServiceImpl {

    @Transactional(readOnly = true)
    CarrinhoResponseDto verCarrinhoUsuario(EmailRequestDto email);

    @Transactional
    CarrinhoResponseDto inserirItemNoCarrinho(InserirItemRequestDto inserirItemRequestDto);
}
