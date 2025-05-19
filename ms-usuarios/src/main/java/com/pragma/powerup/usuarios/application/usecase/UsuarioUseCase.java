package com.pragma.powerup.usuarios.application.usecase;
import com.pragma.powerup.usuarios.domain.api.IUsuarioService;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.usuarios.infraestructure.exception.handler.UnauthorizedRoleException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UsuarioUseCase implements IUsuarioService {

    private final IUsuarioPersistencePort persistencePort;

    @Override
    public void crearPropietario(Usuario usuario, String rolCreador) {
        if (!"ADMINISTRADOR".equalsIgnoreCase(rolCreador.trim())) {
            throw new UnauthorizedRoleException("Solo un administrador puede crear propietarios");
        }

        if (Period.between(LocalDate.parse(usuario.getFechaNacimiento()), LocalDate.now()).getYears() < 18) {
            throw new RuntimeException("El usuario debe ser mayor de edad");
        }

        if (persistencePort.existeCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        usuario.setRol("PROPIETARIO");
        persistencePort.guardarUsuario(usuario);
    }
}