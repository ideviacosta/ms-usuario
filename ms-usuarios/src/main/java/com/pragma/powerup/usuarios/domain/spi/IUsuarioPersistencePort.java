package com.pragma.powerup.usuarios.domain.spi;

import com.pragma.powerup.usuarios.domain.model.Usuario;

public interface IUsuarioPersistencePort {
    boolean existeCorreo(String correo);
    void guardarUsuario(Usuario usuario);
    Usuario obtenerPorId(Long id);

}