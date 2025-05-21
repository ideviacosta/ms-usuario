package com.pragma.powerup.usuarios.infraestructure.input.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginRequestDto {
    private String correo;
    private String clave;
}