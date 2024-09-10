package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LibroService {
    Page<LibroResponse> getLibros(PageRequest pageRequest);
    LibroResponse getLibroByIsbn(int isbn);
    LibroResponse createLibro(LibroRequest libroRequest);
    void deleteLibro(int isbn);  // delete
    LibroResponse updateLibro(int isbn, LibroRequest libroRequest); //put
}