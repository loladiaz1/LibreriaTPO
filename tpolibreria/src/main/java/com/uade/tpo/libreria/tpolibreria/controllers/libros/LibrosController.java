package com.uade.tpo.libreria.tpolibreria.controllers.libros;

import org.springframework.web.bind.annotation.*;

import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.service.LibroService;
import java.net.URI;
import java.util.Base64;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import java.sql.SQLException;

@RestController
@RequestMapping("/libros")
public class LibrosController {

    @Autowired
    private LibroService libroService;

    // Endpoint para buscar libros con filtros combinados
    @GetMapping
    public ResponseEntity<Page<LibroResponse>> getLibros(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(libroService.getLibros(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size)));
    }

    // Endpoint específico para buscar por ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<LibroResponse> getLibroByIsbn(@PathVariable int isbn) {
        LibroResponse result = libroService.getLibroByIsbn(isbn);
        return ResponseEntity.ok(result);
    }

    // Endpoint específico para buscar por título
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Page<LibroResponse>> getLibrosByTitulo(
            @PathVariable String titulo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size)));
    }

    // Endpoint específico para buscar por autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<Page<LibroResponse>> getLibrosByAutor(
            @PathVariable String autor,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size)));
    }

    // Endpoint específico para buscar por editorial
   /*  @GetMapping("/editorial/{editorial}")
    public ResponseEntity<Page<LibroResponse>> getLibrosByEditorial(
            @PathVariable String editorial,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size)));
    }*/

    // Endpoint específico para buscar por idioma
    @GetMapping("/idioma/{idioma}")
    public ResponseEntity<Page<LibroResponse>> getLibrosByIdioma(
            @PathVariable String idioma,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return ResponseEntity.ok(libroService.getLibros(PageRequest.of(page, size)));
    }
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteLibro(@PathVariable int isbn) {
        try {
            libroService.deleteLibro(isbn);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{isbn}")
    public ResponseEntity<LibroResponse> updateLibro(@PathVariable int isbn, @RequestBody LibroRequest libroRequest) {
        try {
            LibroResponse updatedLibro = libroService.updateLibro(isbn, libroRequest);
            return ResponseEntity.ok(updatedLibro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // método para crear un libro usando LibroRequest y LibroService
    @PostMapping
    public ResponseEntity<Object> createLibro(@RequestBody LibroRequest libroRequest) {
        LibroResponse result = libroService.createLibro(libroRequest);
        return ResponseEntity.created(URI.create("/libros/" + result.getIsbn())).body(result);
    }

    @GetMapping("/foto/{isbn}")
    public ResponseEntity<String> getFotoByIsbn(@PathVariable int isbn) throws SQLException {
        LibroResponse result = libroService.getLibroByIsbn(isbn);
        return ResponseEntity.ok(result.getImage());
    }

}
