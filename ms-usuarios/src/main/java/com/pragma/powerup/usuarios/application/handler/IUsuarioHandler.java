package com.pragma.powerup.usuarios.application.handler;

import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioResponseDto;

public interface IUsuarioHandler {
    void crearPropietario(UsuarioRequestDto dto, String rolCreador);
    UsuarioResponseDto obtenerPorId(Long id);

}