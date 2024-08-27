package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.entity.dto.GeneroRequest;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCategoriaDuplicada;
import com.uade.tpo.libreria.tpolibreria.service.GeneroService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("categories")
public class CategoriesController {
    @Autowired
    private GeneroService GeneroService;

    
    /* 
    @GetMapping
    public ResponseEntity<List<Genero>> getCategories() {
        return ResponseEntity.ok(GeneroService.getGeneros());
    }
    */

    @GetMapping("/{GeneroId}")
    public ResponseEntity<Genero> getGeneroById(@PathVariable Long GeneroId) {
        Optional<Genero> result = GeneroService.getGeneroById(GeneroId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Object> createGenero(@RequestBody GeneroRequest GeneroRequest)
            throws ExcepcionCategoriaDuplicada {
        Genero result = GeneroService.createGenero(GeneroRequest.getNombre());
        return ResponseEntity.created(URI.create("/categories/" + result.getId())).body(result);
    }
}
