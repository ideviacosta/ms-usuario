package com.pragma.powerup.usuarios.infraestructure.output.jpa.mapper;

import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.infraestructure.output.jpa.entity.UsuarioEntity;

public class UsuarioEntityMapper {

    public static UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombre(usuario.getNombre());
        entity.setApellido(usuario.getApellido());
        entity.setDocumentoIdentidad(usuario.getDocumentoIdentidad());
        entity.setCelular(usuario.getCelular());
        entity.setCorreo(usuario.getCorreo());
        entity.setClave(usuario.getClave());
        entity.setRol(usuario.getRol());
        entity.setFechaNacimiento(usuario.getFechaNacimiento());
        return entity;
    }

    public static Usuario toModel(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNombre(entity.getNombre());
        usuario.setApellido(entity.getApellido());
        usuario.setDocumentoIdentidad(entity.getDocumentoIdentidad());
        usuario.setCelular(entity.getCelular());
        usuario.setCorreo(entity.getCorreo());
        usuario.setClave(entity.getClave());
        usuario.setRol(entity.getRol());
        usuario.setFechaNacimiento(entity.getFechaNacimiento());
        return usuario;
    }
}