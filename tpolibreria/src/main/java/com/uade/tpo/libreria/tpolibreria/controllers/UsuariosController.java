package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("usuarios")
public class UsuariosController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(
                @RequestParam(required = false) Integer page,
                @RequestParam(required = false) Integer size){
            if (page == null || size == null){
                return ResponseEntity.ok(usuarioService.getUsuarios(PageRequest.of(0, Integer.MAX_VALUE)));
            }
            return ResponseEntity.ok(usuarioService.getUsuarios(PageRequest.of(page, size)));
        }
    
    }
    

