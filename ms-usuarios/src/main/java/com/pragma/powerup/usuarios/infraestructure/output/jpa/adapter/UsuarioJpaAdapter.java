package com.pragma.powerup.usuarios.infraestructure.output.jpa.adapter;

import com.pragma.powerup.usuarios.domain.exception.UsuarioNoEncontrado;
import com.pragma.powerup.usuarios.domain.model.Usuario;
import com.pragma.powerup.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.usuarios.infraestructure.output.jpa.entity.UsuarioEntity;
import com.pragma.powerup.usuarios.infraestructure.output.jpa.mapper.UsuarioEntityMapper;
import com.pragma.powerup.usuarios.infraestructure.output.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import static com.pragma.powerup.usuarios.util.MensajesError.*;

@Component
@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        UsuarioEntity entity = UsuarioEntityMapper.toEntity(usuario);
        usuarioRepository.save(entity);
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioEntityMapper::toModel)
                .orElseThrow(() -> new UsuarioNoEncontrado(USUARIO_NO_ENCONTRADO));
    }

    @Override
    public Usuario obtenerPorCorreo(String correo) {
        UsuarioEntity entity = usuarioRepository.findByCorreo(correo).orElse(null);
        return entity != null ? UsuarioEntityMapper.toModel(entity) : null;
    }

}