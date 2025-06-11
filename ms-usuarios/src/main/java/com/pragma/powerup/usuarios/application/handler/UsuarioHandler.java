package com.pragma.powerup.usuarios.application.handler;

import com.pragma.powerup.usuarios.domain.api.IUsuarioService;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioLoginResponseDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioResponseDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.mapper.UsuarioRequestMapper;
import org.springframework.stereotype.Service;

@Service
public class UsuarioHandler implements IUsuarioHandler {

    private final IUsuarioService usuarioService;

    public UsuarioHandler(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void crearPropietario(UsuarioRequestDto dto, String rolCreador) {
        Usuario usuario = UsuarioRequestMapper.toModel(dto);
        usuarioService.crearPropietario(usuario, rolCreador);
    }

    @Override
    public void crearEmpleado(UsuarioRequestDto dto, String rolCreador) {
        Usuario usuario = UsuarioRequestMapper.toModel(dto);
        usuarioService.crearEmpleado(usuario, rolCreador);
    }

    @Override
    public UsuarioLoginResponseDto login(String correo, String clave) {
        Usuario usuario = usuarioService.login(correo, clave);
        return new UsuarioLoginResponseDto(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getCorreo(), usuario.getRol());
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

    @Override
    public void crearCliente(UsuarioRequestDto dto) {
        Usuario usuario = UsuarioRequestMapper.toModel(dto);
        usuarioService.crearCliente(usuario);
    }

}