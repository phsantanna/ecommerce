package com.ecommerce.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record RespostaExceptionDto(String message, int status, List<ErroCampoDto> erros) {

    public static RespostaExceptionDto conflitoDeDados(String message){
        return new RespostaExceptionDto(message, HttpStatus.CONFLICT.value(), List.of());
    }

    public static RespostaExceptionDto naoEncontrado(String message) {
        return new RespostaExceptionDto(message, HttpStatus.NOT_FOUND.value(), List.of());
    }

    public static RespostaExceptionDto requisicaoInvalida(String message) {
        return new RespostaExceptionDto(message, HttpStatus.BAD_REQUEST.value(), List.of());
    }
}
