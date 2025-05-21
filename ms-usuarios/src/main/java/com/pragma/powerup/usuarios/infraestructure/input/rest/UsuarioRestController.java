package com.pragma.powerup.usuarios.infraestructure.input.rest;

import com.pragma.powerup.usuarios.application.handler.IUsuarioHandler;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.domain.util.JwtUtil;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioLoginRequestDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioLoginResponseDto;
import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioRequestDto;

import com.pragma.powerup.usuarios.infraestructure.input.rest.dto.UsuarioResponseDto;
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


    @PostMapping("/propietario")
    @ResponseStatus(HttpStatus.CREATED)
    public void crearPropietario(@Valid @RequestBody UsuarioRequestDto dto,
                                 @RequestHeader("Rol") String rolCreador) {
        System.out.println("📥 Header Rol recibido: [" + rolCreador + "]");
        usuarioHandler.crearPropietario(dto, rolCreador);
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioHandler.obtenerPorId(id);
    }

    @PostMapping("/empleado")
    @ResponseStatus(HttpStatus.CREATED)
    public void crearEmpleado(@Valid @RequestBody UsuarioRequestDto dto,
                              @RequestHeader("Rol") String rolCreador) {
        System.out.println("📥 Header Rol recibido para empleado: [" + rolCreador + "]");
        usuarioHandler.crearEmpleado(dto, rolCreador);
    }

    @PostMapping("/login")
    public String login(@RequestParam String correo, @RequestParam String clave) {
        UsuarioLoginResponseDto usuario = usuarioHandler.login(correo, clave);
        return jwtUtil.generarToken(usuario.getId(), usuario.getRol());
    }


}