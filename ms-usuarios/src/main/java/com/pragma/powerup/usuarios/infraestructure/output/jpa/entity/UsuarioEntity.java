package com.pragma.powerup.usuarios.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    @Column(name = "numero_documento")
    private String documentoIdentidad;

    private String celular;

    private String correo;

    private String clave;

    private String rol;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
}