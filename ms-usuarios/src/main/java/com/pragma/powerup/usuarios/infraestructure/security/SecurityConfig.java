package com.pragma.powerup.usuarios.infraestructure.security;

import com.pragma.powerup.usuarios.util.JwtUtil;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil) {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtFilter(jwtUtil));
        registrationBean.addUrlPatterns("/usuarios/propietario",
                                        "/usuarios/empleado"); // Aplica solo a endpoints protegidos
        return registrationBean;
    }
}