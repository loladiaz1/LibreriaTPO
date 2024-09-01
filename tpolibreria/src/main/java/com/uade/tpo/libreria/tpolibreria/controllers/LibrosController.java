package com.uade.tpo.libreria.tpolibreria.controllers;
import org.springframework.web.bind.annotation.*;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
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

    @GetMapping
    public ResponseEntity<Page<Libro>> getLibros(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size), titulo, autor));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> getLibroByIsbn(@PathVariable int isbn) {
        Optional<Libro> result = libroService.getLibroByIsbn(isbn);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createLibro(@RequestBody Libro libro) {
        Libro result = libroService.createLibro(libro);
        return ResponseEntity.created(URI.create("/libros/" + result.getIsbn())).body(result);
    }
}

