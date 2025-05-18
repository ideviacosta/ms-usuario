package com.pragma.powerup.usuarios.infraestructure.input.rest.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDto {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @Pattern(regexp = "\\d+", message = "El documento debe ser numérico")
    private String documentoIdentidad;

    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Número de celular inválido")
    private String celular;

    @Email
    private String correo;

    @Size(min = 6)
    private String clave;

    @NotBlank
    private String fechaNacimiento; // formato ISO (yyyy-MM-dd)
}