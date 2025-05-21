package com.pragma.powerup.usuarios.domain.util;


import com.pragma.powerup.usuarios.infraestructure.exception.handler.UnauthorizedRoleException;

public class RolValidator {

    public static void validarRol(String actual, String requerido) {
        if (!requerido.equalsIgnoreCase(actual.trim())) {
            throw new UnauthorizedRoleException("Se requiere rol: " + requerido + ", pero se recibió: " + actual);
        }
    }
}