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

    @Autowired
    private CarritoService CarritoService;

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
            String direccion, int CP) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre_usuario(nombre_usuario);
        nuevoUsuario.setMail(mail);
        nuevoUsuario.setContraseña(contraseña);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellido(apellido);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setCP(CP);
        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public Optional<Usuario> getUsuarioByMail(String UsuarioMail) {
        return usuarioRepository.findByMail(UsuarioMail);
    }
    @Override
    public Optional<Usuario> updateUsuario(Long usuarioId, String nombre, String apellido, String direccion, int CP) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setDireccion(direccion);
            usuario.setCP(CP);
            return Optional.of(usuarioRepository.save(usuario));
        }
        return Optional.empty();
    }

    @Override
    public void deleteUsuario(Long usuarioId) {
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isPresent()) {
            CarritoService.eliminarCarrito(usuario.get().getMail());

            usuarioRepository.deleteById(usuarioId);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
    }
    
}
