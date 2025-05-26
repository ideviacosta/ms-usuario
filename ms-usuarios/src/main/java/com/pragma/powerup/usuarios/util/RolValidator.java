package com.pragma.powerup.usuarios.util;

import com.pragma.powerup.usuarios.infraestructure.exception.handler.UnauthorizedRoleException;

import java.util.Arrays;
import java.util.List;

public class RolValidator {

    private RolValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Valida que el rol actual sea igual al requerido.
     * @param actual   El rol proporcionado.
     * @param requerido El rol esperado.
     */
    public static void validarRol(String actual, String requerido) {
        if (!requerido.equalsIgnoreCase(actual.trim())) {
            throw new UnauthorizedRoleException("Se requiere rol: " + requerido + ", pero se recibió: " + actual);
        }
    }

    /**
     * Valida que el rol actual esté dentro de los roles permitidos.
     * @param actual El rol actual.
     * @param rolesPermitidos Uno o más roles válidos.
     */
    public static void validarRolEn(String actual, String... rolesPermitidos) {
        List<String> permitidos = Arrays.stream(rolesPermitidos)
                .map(String::toUpperCase)
                .toList();

        if (!permitidos.contains(actual.trim().toUpperCase())) {
            throw new UnauthorizedRoleException("Rol no autorizado: " + actual + ". Se esperaba uno de: " + String.join(", ", permitidos));
        }
    }
}
