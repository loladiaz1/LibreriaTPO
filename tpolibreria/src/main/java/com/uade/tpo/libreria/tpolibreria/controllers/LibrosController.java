package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.web.bind.annotation.*;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.dto.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.service.LibroService;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/libros")
public class LibrosController {

    @Autowired
    private LibroService libroService;

    // Endpoint para buscar libros con filtros combinados
    @GetMapping
    public ResponseEntity<Page<Libro>> getLibros(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String idioma) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size), titulo, autor, editorial, idioma));
    }

    // Endpoint específico para buscar por ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> getLibroByIsbn(@PathVariable int isbn) {
        Optional<Libro> result = libroService.getLibroByIsbn(isbn);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Endpoint específico para buscar por título
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<Libro>> getLibrosByTitulo(
            @PathVariable String titulo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size), titulo, null, null, null));
    }

    // Endpoint específico para buscar por autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<Page<Libro>> getLibrosByAutor(
            @PathVariable String autor,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size), null, autor, null, null));
    }

    // Endpoint específico para buscar por editorial
    @GetMapping("/editorial/{editorial}")
    public ResponseEntity<Page<Libro>> getLibrosByEditorial(
            @PathVariable String editorial,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size), null, null, editorial, null));
    }

    // Endpoint específico para buscar por idioma
    @GetMapping("/idioma/{idioma}")
    public ResponseEntity<Page<Libro>> getLibrosByIdioma(
            @PathVariable String idioma,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size), null, null, null, idioma));
    }

    // método para crear un libro usando LibroRequest y LibroService
    @PostMapping
    public ResponseEntity<Object> createLibro(@RequestBody LibroRequest libroRequest) {
        Libro result = libroService.createLibro(libroRequest);
        return ResponseEntity.created(URI.create("/libros/" + result.getIsbn())).body(result);
    }
}
