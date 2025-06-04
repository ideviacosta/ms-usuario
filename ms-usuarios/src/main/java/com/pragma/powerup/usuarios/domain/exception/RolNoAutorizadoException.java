package com.pragma.powerup.usuarios.domain.exception;

public class RolNoAutorizadoException extends RuntimeException {
    public RolNoAutorizadoException(String message) {
        super(message);
    }
}
