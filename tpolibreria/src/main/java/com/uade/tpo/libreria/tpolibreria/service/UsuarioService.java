package com.uade.tpo.libreria.tpolibreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.libreria.tpolibreria.entity.Usuario;

public interface UsuarioService {
    public Page<Usuario> getUsuarios(PageRequest pageRequest);

}
