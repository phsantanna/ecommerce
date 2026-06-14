package com.ecommerce.exceptions;

import com.ecommerce.enums.UsuarioErroTipo;

public class UsuarioException extends RuntimeException {
    private final UsuarioErroTipo tipo;
    public UsuarioException(UsuarioErroTipo tipo) {
        super(tipo.getMensagem());
        this.tipo = tipo;
    }

    public UsuarioErroTipo getTipo() {
        return tipo;
    }
}
