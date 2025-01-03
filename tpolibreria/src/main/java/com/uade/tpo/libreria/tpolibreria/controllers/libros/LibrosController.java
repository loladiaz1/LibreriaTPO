package com.uade.tpo.libreria.tpolibreria.controllers.libros;
 
import org.springframework.web.bind.annotation.*;
 
import com.uade.tpo.libreria.tpolibreria.service.LibroService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.SQLException;
 
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/libros")
public class LibrosController {
 
    @Autowired
    private LibroService libroService;
 
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
    public ResponseEntity<LibroResponse> getLibroByIsbn(@PathVariable Long isbn) {
        LibroResponse result = libroService.getLibroByIsbn(isbn);
        return ResponseEntity.ok(result);
    }
 
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<LibroResponse>> getLibroByTitulo(@PathVariable String titulo) {
        List<LibroResponse> result = libroService.getLibroByTitulo(titulo);
        return ResponseEntity.ok(result);
    }
   
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LibroResponse>> getLibroByAutor(@PathVariable String autor) {
        List<LibroResponse> result = libroService.getLibroByAutor(autor);
        return ResponseEntity.ok(result);
    }
   
    @GetMapping("/editorial/{editorial}")
    public ResponseEntity<List<LibroResponse>> getLibroByEditorial(@PathVariable String editorial) {
        List<LibroResponse> result = libroService.getLibroByEditorial(editorial);
        return ResponseEntity.ok(result);
    }
   
    @GetMapping("/idioma/{idioma}")
    public ResponseEntity<List<LibroResponse>> getLibroByIdioma(@PathVariable String idioma) {
        List<LibroResponse> result = libroService.getLibroByIdioma(idioma);
        return ResponseEntity.ok(result);
    }
 
 
    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteLibro(@PathVariable Long isbn) {
        try {
            libroService.deleteLibro(isbn);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/{isbn}")
    public ResponseEntity<LibroResponse> updateLibro(@PathVariable Long isbn, @RequestBody LibroRequest libroRequest) {
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
    public ResponseEntity<String> getFotoByIsbn(@PathVariable Long isbn) throws SQLException {
        LibroResponse result = libroService.getLibroByIsbn(isbn);
        return ResponseEntity.ok(result.getImage());
    }
 
    @GetMapping("/generoId/{id}")
    public ResponseEntity<Page<LibroResponse>> getLibrosByGeneroId(
            @PathVariable Long id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        // Si los parámetros page o size no son proporcionados:
        if (page == null || size == null) {
            return ResponseEntity.ok(libroService.getLibrosByGeneroId(id, PageRequest.of(0, Integer.MAX_VALUE)));
        }

        // Si los parámetros page y size son proporcionados:
        return ResponseEntity.ok(libroService.getLibrosByGeneroId(id, PageRequest.of(page, size)));
    }

}