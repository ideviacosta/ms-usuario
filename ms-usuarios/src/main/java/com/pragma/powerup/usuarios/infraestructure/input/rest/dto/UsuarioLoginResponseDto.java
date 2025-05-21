package com.pragma.powerup.usuarios.infraestructure.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioLoginResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
    private String token;
}