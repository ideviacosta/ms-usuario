package com.pragma.powerup.usuarios.application.usecase;
import com.pragma.powerup.usuarios.domain.api.IUsuarioService;
import com.pragma.powerup.usuarios.domain.exception.CredencialesInvalidasException;
import com.pragma.powerup.usuarios.domain.exception.UsuarioMenorEdadException;
import com.pragma.powerup.usuarios.domain.exception.UsuarioYaExisteException;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.domain.spi.IUsuarioPersistencePort;
import lombok.RequiredArgsConstructor;

import static com.pragma.powerup.usuarios.util.MensajesError.*;
import static com.pragma.powerup.usuarios.util.RolValidator.validarRol;


import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UsuarioUseCase implements IUsuarioService {

    private final IUsuarioPersistencePort persistencePort;

    @Override
    public void crearPropietario(Usuario usuario, String rolCreador) {
        validarRol(rolCreador, "ADMINISTRADOR");

        if (esMenorDeEdad(usuario.getFechaNacimiento())) {
            throw new UsuarioMenorEdadException(USUARIO_MENOR_EDAD);
        }

        if (persistencePort.existeCorreo(usuario.getCorreo())) {
            throw new UsuarioYaExisteException(CORREO_YA_REGISTRADO);
        }

        usuario.setRol("PROPIETARIO");
        persistencePort.guardarUsuario(usuario);
    }

    @Override
    public void crearEmpleado(Usuario usuario, String rolCreador) {
        validarRol(rolCreador, "PROPIETARIO");

        if (esMenorDeEdad(usuario.getFechaNacimiento())) {
            throw new UsuarioMenorEdadException(USUARIO_MENOR_EDAD);
        }

        if (persistencePort.existeCorreo(usuario.getCorreo())) {
            throw new UsuarioYaExisteException(CORREO_YA_REGISTRADO);
        }

        usuario.setRol("EMPLEADO");
        persistencePort.guardarUsuario(usuario);
    }

    @Override
    public Usuario login(String correo, String clave) {
        Usuario usuario = persistencePort.obtenerPorCorreo(correo);
        if (usuario == null || !usuario.getClave().equals(clave)) {
            throw new CredencialesInvalidasException(CREDENCIALES_INVALIDAS);
        }
        return usuario;
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return persistencePort.obtenerPorId(id);
    }

    private boolean esMenorDeEdad(String fechaNacimiento) {
        return Period.between(LocalDate.parse(fechaNacimiento), LocalDate.now()).getYears() < 18;
    }
}