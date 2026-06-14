package com.ecommerce.exceptions;

import com.ecommerce.enums.CarrinhoErroTipo;

public class CarrinhoException extends RuntimeException {
    private final CarrinhoErroTipo tipo;

    public CarrinhoException(CarrinhoErroTipo tipo) {
        super(tipo.getMensagem());
        this.tipo = tipo;
    }

    public CarrinhoErroTipo getTipo() {
        return tipo;
    }
}
