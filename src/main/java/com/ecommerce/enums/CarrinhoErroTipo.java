package com.ecommerce.enums;

import org.springframework.http.HttpStatus;

public enum CarrinhoErroTipo {
    CARRINHO_JA_EXISTENTE("O usuário já possui um carrinho ativo.", HttpStatus.CONFLICT),
    CARRINHO_NAO_ENCONTRADO("Carrinho não encontrado para o ID informado.", HttpStatus.NOT_FOUND),
    ITEM_SEM_ESTOQUE("A quantidade solicitada excede o estoque disponível.", HttpStatus.BAD_REQUEST),
    USUARIO_NAO_ENCONTRADO("Usuário não encontrado no sistema.", HttpStatus.NOT_FOUND),
    PRODUTO_NAO_ENCONTRADO("Produto não encontrado no catálogo.", HttpStatus.NOT_FOUND),
    CARRINHO_VAZIO("Não é possível finalizar a compra pois o carrinho está vazio.", HttpStatus.BAD_REQUEST);

    private final String mensagem;
    private final HttpStatus status;

    CarrinhoErroTipo(String mensagem, HttpStatus status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getMensagem() { return mensagem; }
    public HttpStatus getStatus() { return status; }
}