package com.pragma.powerup.usuarios.domain.api;

import com.pragma.powerup.usuarios.domain.model.Usuario;

public interface IUsuarioService {
    void crearPropietario(Usuario usuario, String rolCreador);
    Usuario obtenerPorId(Long id);

}