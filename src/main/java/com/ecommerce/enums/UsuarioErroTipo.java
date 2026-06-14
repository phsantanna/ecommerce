package com.ecommerce.enums;

import org.springframework.http.HttpStatus;

public enum UsuarioErroTipo {

    USUARIO_NAO_ENCONTRADO("Usuário não encontrado no sistema.", HttpStatus.NOT_FOUND),
    EMAIL_JA_CADASTRADO("Email já cadastrado", HttpStatus.BAD_REQUEST),
    CELULAR_JA_CADASTRADO("Celular já cadastrado", HttpStatus.BAD_REQUEST),;

    private final String mensagem;
    private final HttpStatus status;

    UsuarioErroTipo(String mensagem, HttpStatus status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getMensagem() { return mensagem; }
    public HttpStatus getStatus() { return status; }
}
