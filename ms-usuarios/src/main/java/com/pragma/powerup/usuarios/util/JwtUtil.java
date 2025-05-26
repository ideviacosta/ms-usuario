package com.pragma.powerup.usuarios.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private final Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generarToken(Long idUsuario, String rol) {
        return Jwts.builder()
                .setSubject(String.valueOf(idUsuario))
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validarTokenYObtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extraerIdUsuario(String token) {
        return Long.valueOf(validarTokenYObtenerClaims(token).getSubject());
    }

    public String extraerRol(String token) {
        return validarTokenYObtenerClaims(token).get("rol", String.class);
    }
}
