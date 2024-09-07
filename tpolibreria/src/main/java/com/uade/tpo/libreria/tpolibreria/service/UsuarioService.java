package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;

public interface UsuarioService {
    public Page<Usuario> getUsuarios(PageRequest pageRequest);

    public Optional<Usuario> getUsuarioById(Long UsuarioId);

    public Optional<Usuario> getUsuarioByMail(String UsuarioMail);

    public Usuario createUsuario(String nombre_usuario, String mail, String contraseña, String nombre, String apellido, String direccion, int CP);

}
