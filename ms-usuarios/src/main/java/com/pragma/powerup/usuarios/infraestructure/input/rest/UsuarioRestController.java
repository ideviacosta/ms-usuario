package com.pragma.powerup.usuarios.infraestructure.input.rest;

import com.pragma.powerup.usuarios.application.handler.IUsuarioHandler;
import com.pragma.powerup.usuarios.util.JwtUtil;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioLoginResponseDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;

import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final IUsuarioHandler usuarioHandler;
    private final JwtUtil jwtUtil;


    @Operation(summary = "Crear cuenta de Propietario", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario menor de edad"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado")
    })
    @PostMapping("/propietario")
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPropietario(@Valid @RequestBody UsuarioRequestDto dto,
                                 HttpServletRequest request) {
        String rolCreador = (String) request.getAttribute("rolUsuario");
        usuarioHandler.crearEmpleado(dto, rolCreador);
    }

    @Operation(summary = "Obtener usuario by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Datos obtenidos correctamente"),
            @ApiResponse(responseCode = "400", description = "No se encuentra ese Id"),
    })
    @GetMapping("/{id}")
    public UsuarioResponseDto obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioHandler.obtenerPorId(id);
    }

    @Operation(summary = "Crear cuenta de empleado", security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario menor de edad"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado")
    })
    @PostMapping("/empleado")
    @ResponseStatus(HttpStatus.CREATED)
    public void crearEmpleado(@Valid @RequestBody UsuarioRequestDto dto,
                              HttpServletRequest request) {
        String rolCreador = (String) request.getAttribute("rolUsuario");
        usuarioHandler.crearEmpleado(dto, rolCreador);
    }

    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login Succesfull"),
            @ApiResponse(responseCode = "400", description = "Correo no válido"),
            @ApiResponse(responseCode = "403", description = "Rol no autorizado")
    })
    @PostMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String clave) {
        UsuarioLoginResponseDto usuario = usuarioHandler.login(correo, clave);
        return jwtUtil.generarToken(usuario.getId(), usuario.getRol());
    }

}