package com.pragma.powerup.usuarios.infraestructure.input.rest.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
}