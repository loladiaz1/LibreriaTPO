package com.uade.tpo.libreria.tpolibreria.service;
 
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
 
import java.util.Optional;
 
public interface LibroService {
    Page<LibroResponse> getLibros(PageRequest pageRequest);
    LibroResponse getLibroByIsbn(int isbn);
    Optional<LibroResponse> getLibroByTitulo(String titulo);
    Optional<LibroResponse> getLibroByAutor(String autor);
    Optional<LibroResponse> getLibroByEditorial(String editorial);
    Optional<LibroResponse> getLibroByIdioma(String idioma);
    LibroResponse createLibro(LibroRequest libroRequest);
    void deleteLibro(int isbn);  // delete
    LibroResponse updateLibro(int isbn, LibroRequest libroRequest); //put
}