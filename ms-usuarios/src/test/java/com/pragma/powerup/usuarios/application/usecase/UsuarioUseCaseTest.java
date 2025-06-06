package com.pragma.powerup.usuarios.application.usecase;

import com.pragma.powerup.usuarios.domain.exception.CredencialesInvalidasException;
import com.pragma.powerup.usuarios.domain.exception.UsuarioMenorEdadException;
import com.pragma.powerup.usuarios.domain.exception.UsuarioYaExisteException;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.usuarios.infraestructure.exception.handler.UnauthorizedRoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static com.pragma.powerup.usuarios.util.Roles.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioUseCaseTest {

    private IUsuarioPersistencePort persistencePort;
    private UsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IUsuarioPersistencePort.class);
        useCase = new UsuarioUseCase(persistencePort);
    }

    @Test
    void crearPropietario_exitoso() {
        Usuario usuario = crearUsuarioValido();
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(false);

        assertDoesNotThrow(() -> useCase.crearPropietario(usuario, "ADMINISTRADOR"));

        verify(persistencePort).guardarUsuario(any(Usuario.class));
        assertEquals(PROPIETARIO, usuario.getRol());
    }

    @Test
    void crearPropietario_menorEdad_lanzaExcepcion() {
        Usuario usuario = crearUsuarioValido();
        usuario.setFechaNacimiento(LocalDate.now().minusYears(17).toString());

        assertThrows(UsuarioMenorEdadException.class, () -> useCase.crearPropietario(usuario, "ADMINISTRADOR"));
    }

    @Test
    void crearPropietario_correoExistente_lanzaExcepcion() {
        Usuario usuario = crearUsuarioValido();
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(true);

        assertThrows(UsuarioYaExisteException.class, () -> useCase.crearPropietario(usuario, "ADMINISTRADOR"));
    }

    @Test
    void login_credencialesValidas() {
        Usuario usuario = crearUsuarioValido();
        when(persistencePort.obtenerPorCorreo(usuario.getCorreo())).thenReturn(usuario);

        Usuario result = useCase.login(usuario.getCorreo(), usuario.getClave());

        assertNotNull(result);
        assertEquals(usuario.getCorreo(), result.getCorreo());
    }

    @Test
    void login_credencialesInvalidas_lanzaExcepcion() {
        Usuario usuario = crearUsuarioValido();
        usuario.setClave("claveCorrecta");
        when(persistencePort.obtenerPorCorreo(usuario.getCorreo())).thenReturn(usuario);

        assertThrows(CredencialesInvalidasException.class, () -> useCase.login(usuario.getCorreo(), "claveIncorrecta"));
    }

    private Usuario crearUsuarioValido() {
        Usuario usuario = Usuario.builder().build();
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setCorreo("juan@example.com");
        usuario.setClave("clave123");
        usuario.setFechaNacimiento("2000-01-01");
        return usuario;
    }
    private Usuario buildUsuario(String fechaNacimiento) {
        Usuario usuario = Usuario.builder().build();
        usuario.setNombre("Test");
        usuario.setApellido("User");
        usuario.setCorreo("test@correo.com");
        usuario.setClave("clave123");
        usuario.setDocumentoIdentidad("12345678");
        usuario.setCelular("+573001112233");
        usuario.setFechaNacimiento(fechaNacimiento);
        return usuario;
    }

    @Test
    void crearEmpleado_DeberiaGuardarEmpleadoCorrectamente() {
        Usuario empleado = crearUsuarioValido();
        String rolCreador = PROPIETARIO;

        when(persistencePort.existeCorreo(empleado.getCorreo())).thenReturn(false);

        useCase.crearEmpleado(empleado, rolCreador);

        assertEquals(EMPLEADO, empleado.getRol());
        verify(persistencePort).guardarUsuario(empleado);
    }

    @Test
    void crearCliente_DeberiaGuardarClienteCorrectamente() {
        Usuario cliente = crearUsuarioValido();
        String rolCreador = CLIENTE;

        when(persistencePort.existeCorreo(cliente.getCorreo())).thenReturn(false);

        useCase.crearCliente(cliente, rolCreador);

        assertEquals(CLIENTE, cliente.getRol());
        verify(persistencePort).guardarUsuario(cliente);
    }

    @Test
    void obtenerPorId_DeberiaRetornarUsuario() {
        Long id = 1L;
        Usuario esperado = crearUsuarioValido();
        when(persistencePort.obtenerPorId(id)).thenReturn(esperado);

        Usuario resultado = useCase.obtenerPorId(id);

        assertEquals(esperado, resultado);
    }

    @Test
    void crearPropietario_DeberiaLanzarExcepcion_SiUsuarioEsMenorDeEdad() {
        Usuario menorEdad = crearUsuarioValido();
        menorEdad.setFechaNacimiento(LocalDate.now().minusYears(17).toString());

        assertThrows(UsuarioMenorEdadException.class, () ->
                useCase.crearPropietario(menorEdad, ADMINISTRADOR));
    }

    @Test
    void crearPropietario_DeberiaLanzarExcepcion_SiCorreoYaExiste() {
        Usuario usuario = crearUsuarioValido();
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(true);

        assertThrows(UsuarioYaExisteException.class, () ->
                useCase.crearPropietario(usuario, ADMINISTRADOR));
    }

    @Test
    void crearPropietario_DeberiaLanzarExcepcion_SiRolNoEsAdministrador() {
        Usuario usuario = crearUsuarioValido();

        assertThrows(UnauthorizedRoleException.class, () ->
                useCase.crearPropietario(usuario, EMPLEADO));
    }

    @Test
    void crearEmpleado_DeberiaLanzarExcepcion_SiEsMenorDeEdad() {
        // Arrange
        Usuario usuario = buildUsuario("2008-01-01"); // menor de edad
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(false);

        // Act & Assert
        assertThrows(UsuarioMenorEdadException.class, () ->
                useCase.crearEmpleado(usuario, PROPIETARIO));
    }

    @Test
    void crearEmpleado_DeberiaLanzarExcepcion_SiCorreoYaExiste() {
        // Arrange
        Usuario usuario = buildUsuario("2000-01-01"); // mayor de edad
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(true);

        // Act & Assert
        assertThrows(UsuarioYaExisteException.class, () ->
                useCase.crearEmpleado(usuario, PROPIETARIO));
    }

    @Test
    void crearCliente_DeberiaLanzarExcepcion_SiEsMenorDeEdad() {
        // Arrange
        Usuario usuario = buildUsuario("2009-05-01"); // menor de edad
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(false);

        // Act & Assert
        assertThrows(UsuarioMenorEdadException.class, () ->
                useCase.crearCliente(usuario, CLIENTE));
    }

    @Test
    void crearCliente_DeberiaLanzarExcepcion_SiCorreoYaExiste() {
        // Arrange
        Usuario usuario = buildUsuario("1990-05-01"); // mayor de edad
        when(persistencePort.existeCorreo(usuario.getCorreo())).thenReturn(true);

        // Act & Assert
        assertThrows(UsuarioYaExisteException.class, () ->
                useCase.crearCliente(usuario, CLIENTE));
    }


}