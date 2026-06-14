package com.ecommerce.exceptions;

import com.ecommerce.enums.ProdutoErroTipo;

public class ProdutoException extends RuntimeException {
    private final ProdutoErroTipo tipo;

    public ProdutoException(ProdutoErroTipo tipo) {
        super(tipo.getMensagem());
        this.tipo = tipo;
    }

    public ProdutoErroTipo getTipo() {
        return tipo;
    }
}
