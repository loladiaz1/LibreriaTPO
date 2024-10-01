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
 
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<LibroResponse> getLibroByTitulo(@PathVariable String titulo) {
        Optional<LibroResponse> result = libroService.getLibroByTitulo(titulo);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping("/autor/{autor}")
    public ResponseEntity<LibroResponse> getLibroByAutor(@PathVariable String autor) {
        Optional<LibroResponse> result = libroService.getLibroByAutor(autor);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping("/editorial/{editorial}")
    public ResponseEntity<LibroResponse> getLibroByEditorial(@PathVariable String editorial) {
        Optional<LibroResponse> result = libroService.getLibroByEditorial(editorial);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
    }
   
    @GetMapping("/idioma/{idioma}")
    public ResponseEntity<LibroResponse> getLibroByIdioma(@PathVariable String idioma) {
        Optional<LibroResponse> result = libroService.getLibroByIdioma(idioma);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        return ResponseEntity.noContent().build();
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
 