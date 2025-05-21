package com.pragma.powerup.usuarios.application.usecase;
import com.pragma.powerup.usuarios.domain.api.IUsuarioService;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.usuarios.infraestructure.exception.handler.UnauthorizedRoleException;
import lombok.RequiredArgsConstructor;
import static com.pragma.powerup.usuarios.domain.util.RolValidator.validarRol;


import java.time.LocalDate;
import java.time.Period;

@RequiredArgsConstructor
public class UsuarioUseCase implements IUsuarioService {

    private final IUsuarioPersistencePort persistencePort;

    @Override
    public void crearPropietario(Usuario usuario, String rolCreador) {
        validarRol(rolCreador, "ADMINISTRADOR");

        if (Period.between(LocalDate.parse(usuario.getFechaNacimiento()), LocalDate.now()).getYears() < 18) {
            throw new RuntimeException("El usuario debe ser mayor de edad");
        }

        if (persistencePort.existeCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        usuario.setRol("PROPIETARIO");
        persistencePort.guardarUsuario(usuario);
    }

    @Override
    public void crearEmpleado(Usuario usuario, String rolCreador) {
        validarRol(rolCreador, "PROPIETARIO");

        if (Period.between(LocalDate.parse(usuario.getFechaNacimiento()), LocalDate.now()).getYears() < 18) {
            throw new RuntimeException("El usuario debe ser mayor de edad");
        }

        if (persistencePort.existeCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        usuario.setRol("EMPLEADO");
        persistencePort.guardarUsuario(usuario);
    }

    @Override
    public Usuario login(String correo, String clave) {
        Usuario usuario = persistencePort.obtenerPorCorreo(correo);
        if (usuario == null || !usuario.getClave().equals(clave)) {
            throw new RuntimeException("Credenciales inválidas");
        }
        return usuario;
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return persistencePort.obtenerPorId(id);
    }
}