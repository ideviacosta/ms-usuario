package com.pragma.powerup.usuarios.application.handler;

import com.pragma.powerup.usuarios.domain.api.IUsuarioService;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioResponseDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.mapper.UsuarioRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioHandler implements IUsuarioHandler {

    private final IUsuarioService usuarioService;

    @Override
    public void crearPropietario(UsuarioRequestDto dto, String rolCreador) {
        Usuario usuario = UsuarioRequestMapper.toModel(dto);
        usuarioService.crearPropietario(usuario, rolCreador);
    }

    @Override
    public UsuarioResponseDto obtenerPorId(Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(usuario.getRol());
        return dto;
    }
}