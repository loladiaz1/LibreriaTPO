package com.uade.tpo.libreria.tpolibreria.service;

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
    
    
}
