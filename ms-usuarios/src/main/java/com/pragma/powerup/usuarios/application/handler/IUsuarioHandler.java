package com.pragma.powerup.usuarios.application.handler;

import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioLoginResponseDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioResponseDto;

public interface IUsuarioHandler {
    void crearPropietario(UsuarioRequestDto dto, String rolCreador);
    UsuarioResponseDto obtenerPorId(Long id);
    void crearEmpleado(UsuarioRequestDto dto, String rolCreador);
    UsuarioLoginResponseDto login(String correo, String clave);
    void crearCliente(UsuarioRequestDto dto, String rolCreador);

}