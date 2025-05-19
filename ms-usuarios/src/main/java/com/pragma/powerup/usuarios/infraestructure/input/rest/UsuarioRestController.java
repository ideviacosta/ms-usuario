package com.pragma.powerup.usuarios.infraestructure.input.rest;

import com.pragma.powerup.usuarios.application.handler.IUsuarioHandler;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final IUsuarioHandler usuarioHandler;

    @PostMapping("/propietario")
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPropietario(@Valid @RequestBody UsuarioRequestDto dto,
                                 @RequestHeader("Rol") String rolCreador) {
        System.out.println("📥 Header Rol recibido: [" + rolCreador + "]");
        usuarioHandler.crearPropietario(dto, rolCreador);
    }
}