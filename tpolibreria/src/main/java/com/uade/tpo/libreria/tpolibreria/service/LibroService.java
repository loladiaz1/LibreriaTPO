package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LibroService {
    Page<Libro> getLibros(PageRequest pageRequest, String titulo, String autor, String editorial, String idioma);
    Optional<Libro> getLibroByIsbn(int isbn);
    Libro createLibro(LibroRequest libroRequest);
    void deleteLibro(int isbn);  // delete
    Libro updateLibro(int isbn, LibroRequest libroRequest); //put
}