package com.uade.tpo.libreria.tpolibreria.controllers.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.net.URI;



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

    @GetMapping("/{UsuarioId}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long UsuarioId) {
        Optional<Usuario> result = usuarioService.getUsuarioById(UsuarioId);
        if (result.isPresent()){
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<Object> createUsuario(@RequestBody UsuarioRequest ur) {
        Usuario result = usuarioService.createUsuario(ur.getNombre_usuario(), ur.getMail(), ur.getContraseña(), ur.getNombre(), ur.getApellido(), ur.getDireccion(), ur.getCP());
        return ResponseEntity.created(URI.create("/usuarios/" + result.getId())).body(result);
    }
    
    }
    

