package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> getUsuarios(PageRequest pageable) {
        return usuarioRepository.findAll(pageable);
       
    }

    @Override
    public Optional<Usuario> getUsuarioById(Long UsuarioId) {
        return usuarioRepository.findById(UsuarioId);
    }

    @Override
    public Usuario createUsuario(String nombre_usuario, String mail, String contraseña, String nombre, String apellido,
            String direccion, int CP, String rol) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre_usuario(nombre_usuario);
        nuevoUsuario.setMail(mail);
        nuevoUsuario.setContraseña(contraseña);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setCP(CP);
        nuevoUsuario.setRol(rol);
        return usuarioRepository.save(nuevoUsuario);
    }
    
    
}
