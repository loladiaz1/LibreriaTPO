package com.uade.tpo.libreria.tpolibreria.service;
 
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;

public interface LibroService {
    Page<LibroResponse> getLibros(PageRequest pageRequest);
    LibroResponse getLibroByIsbn(Long isbn);
    List<LibroResponse> getLibroByTitulo(String titulo);
    List<LibroResponse> getLibroByAutor(String autor);
    List<LibroResponse> getLibroByEditorial(String editorial);
    List<LibroResponse> getLibroByIdioma(String idioma);
    LibroResponse createLibro(LibroRequest libroRequest);
    void deleteLibro(Long isbn);  // delete
    LibroResponse updateLibro(Long isbn, LibroRequest libroRequest); //put
}