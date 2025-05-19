package com.pragma.powerup.usuarios.infraestructure.exception.handler;
public class UnauthorizedRoleException extends RuntimeException {
    public UnauthorizedRoleException(String message) {
        super(message);
    }
}