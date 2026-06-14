package com.ecommerce.exceptions;

import com.ecommerce.controller.dto.RespostaExceptionDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CarrinhoExceptionHandler {

    @ExceptionHandler(CarrinhoException.class)
    public RespostaExceptionDto carrinhoException(CarrinhoException e) {
        return switch (e.getTipo()) {
            case CARRINHO_JA_EXISTENTE -> RespostaExceptionDto.conflitoDeDados(e.getMessage()); //erros relacionados a conflito de dados
            case CARRINHO_NAO_ENCONTRADO,
                 USUARIO_NAO_ENCONTRADO,
                 PRODUTO_NAO_ENCONTRADO -> RespostaExceptionDto.naoEncontrado(e.getMessage()); //erro relacionais a regra de negócio / validação
            case ITEM_SEM_ESTOQUE, CARRINHO_VAZIO -> RespostaExceptionDto.requisicaoInvalida(e.getMessage()); //erros relacionados a requisição inválida
        };
    }
}
