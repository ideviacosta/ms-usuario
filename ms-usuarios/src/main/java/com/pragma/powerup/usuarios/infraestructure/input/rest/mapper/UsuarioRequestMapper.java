package com.pragma.powerup.usuarios.infraestructure.input.rest.mapper;

import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;

public class UsuarioRequestMapper {
    public static Usuario toModel(UsuarioRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setDocumentoIdentidad(dto.getDocumentoIdentidad());
        usuario.setCelular(dto.getCelular());
        usuario.setCorreo(dto.getCorreo());
        usuario.setClave(dto.getClave());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        return usuario;
    }
}
