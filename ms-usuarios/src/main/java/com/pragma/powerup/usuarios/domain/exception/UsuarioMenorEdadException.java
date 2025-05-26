package com.pragma.powerup.usuarios.domain.exception;

public class UsuarioMenorEdadException extends RuntimeException {
    public UsuarioMenorEdadException(String message) {
        super(message);
    }
}