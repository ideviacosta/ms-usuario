package com.pragma.powerup.usuarios.infraestructure.config;

import com.pragma.powerup.usuarios.application.usecase.UsuarioUseCase;
import com.pragma.powerup.usuarios.domain.api.IUsuarioService;
import com.pragma.powerup.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.usuarios.domain.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    @Bean
    public IUsuarioService usuarioService() {
        return new UsuarioUseCase(usuarioPersistencePort);
    }

    @Bean
    public JwtUtil jwtUtil(@Value("${jwt.secret}") String secret) {
        return new JwtUtil(secret);
    }
}