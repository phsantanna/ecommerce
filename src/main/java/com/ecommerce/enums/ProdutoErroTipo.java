package com.ecommerce.enums;

import org.springframework.http.HttpStatus;

public enum ProdutoErroTipo {

    PRODUTO_NAO_ENCONTRADO("Produto não encontrado no sistema.", HttpStatus.NOT_FOUND),
    PRODUTO_JA_EXISTE_NO_CARRINHO("O produto já existe no carrinho.", HttpStatus.CONFLICT);;

    private final String mensagem;
    private final HttpStatus httpStatus;

    ProdutoErroTipo(String mensagem, HttpStatus httpStatus) {
        this.mensagem = mensagem;
        this.httpStatus = httpStatus;
    }

    public String getMensagem() {
        return mensagem;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}