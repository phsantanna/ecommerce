package com.example.demo.infra.security;

import com.example.demo.exceptions.CarrinhoNaoEncontradoException;
import com.example.demo.exceptions.CarrinhoVazioException;
import com.example.demo.exceptions.ProdutoNaoEncontradoException;
import com.example.demo.exceptions.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(CarrinhoNaoEncontradoException.class)
    private ResponseEntity<String> CarrinhoNaoEncontradoHandler(CarrinhoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()) ;
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    private ResponseEntity<String> UsuarioNaoEncontradoHandler(UsuarioNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()) ;
    }
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    private ResponseEntity<String> ProdutoNaoEncontradoHandler(ProdutoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage()) ;
    }
    @ExceptionHandler(CarrinhoVazioException.class)
    private ResponseEntity<String> CarrinhoVazioExceptionHandler(CarrinhoVazioException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage()) ;
    }
}
