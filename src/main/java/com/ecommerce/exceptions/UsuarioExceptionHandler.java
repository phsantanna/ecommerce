package com.ecommerce.exceptions;

import com.ecommerce.controller.dto.RespostaExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UsuarioExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RespostaExceptionDto usuarioException(UsuarioException e) {
        return RespostaExceptionDto.conflitoDeDados(e.getMessage());
    }

}
