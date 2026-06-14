package com.ecommerce.exceptions;

import com.ecommerce.controller.dto.RespostaExceptionDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProdutoExceptionHandler {

    @ExceptionHandler(ProdutoException.class)
    public RespostaExceptionDto handleProdutoException(ProdutoException e) {
        return switch (e.getTipo()) {
            case PRODUTO_NAO_ENCONTRADO -> RespostaExceptionDto.naoEncontrado(e.getMessage());
            case PRODUTO_JA_EXISTE_NO_CARRINHO -> RespostaExceptionDto.conflitoDeDados(e.getMessage());
        };
    }
}
