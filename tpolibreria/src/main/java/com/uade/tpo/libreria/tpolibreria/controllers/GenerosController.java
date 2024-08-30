package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.entity.dto.GeneroRequest;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionGeneroDuplicado;
import com.uade.tpo.libreria.tpolibreria.service.GeneroService;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("generos")
public class GenerosController {
    @Autowired
    private GeneroService GeneroService;

    @GetMapping
    public ResponseEntity<Page<Genero>> getGeneros(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(GeneroService.getGeneros(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(GeneroService.getGeneros(PageRequest.of(page, size)));
    }

    @GetMapping("/{GeneroId}")
    public ResponseEntity<Genero> getGeneroById(@PathVariable Long GeneroId) {
        Optional<Genero> result = GeneroService.getGeneroById(GeneroId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createGenero(@RequestBody GeneroRequest GeneroRequest)
            throws ExcepcionGeneroDuplicado {
        Genero result = GeneroService.createGenero(GeneroRequest.getNombre());
        return ResponseEntity.created(URI.create("/generos/" + result.getId())).body(result);
    }
}
