package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.entity.dto.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;
import com.uade.tpo.libreria.tpolibreria.repository.GeneroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public Page<Libro> getLibros(PageRequest pageable, String titulo, String autor, String editorial, String idioma) {
        List<Libro> libros;
        // Filtrar por los criterios disponibles
        if (titulo != null && !titulo.isEmpty()) {
            libros = libroRepository.findByTituloContaining(titulo);
        } else if (autor != null && !autor.isEmpty()) {
            libros = libroRepository.findByAutorContaining(autor);
        } else if (editorial != null && !editorial.isEmpty()) {
            libros = libroRepository.findByEditorialContaining(editorial);
        } else if (idioma != null && !idioma.isEmpty()) {
            libros = libroRepository.findByIdiomaContaining(idioma);
        } else {
            return libroRepository.findAll(pageable);
        }
        return new PageImpl<>(libros, pageable, libros.size());
    }

    @Override
    public Optional<Libro> getLibroByIsbn(int isbn) {
        return libroRepository.findById(isbn);
    }

    @Override
    public Libro createLibro(LibroRequest libroRequest) {
        Libro libro = new Libro();
        libro.setIsbn(libroRequest.getIsbn());
        libro.setTitulo(libroRequest.getTitulo());
        libro.setPrecio(libroRequest.getPrecio());
        libro.setCantPaginas(libroRequest.getCantPaginas());
        libro.setDescripcion(libroRequest.getDescripcion());
        libro.setStock(libroRequest.getStock());
        libro.setEditorial(libroRequest.getEditorial());
        libro.setEdicion(libroRequest.getEdicion());
        libro.setIdioma(libroRequest.getIdioma());
        libro.setAutor(libroRequest.getAutor());

        // Manejo de la asignación del género
        if (libroRequest.getGeneroId() != null) {
            Genero genero = generoRepository.findById(libroRequest.getGeneroId())
                .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + libroRequest.getGeneroId()));
            libro.setGenero(genero);
        }

        return libroRepository.save(libro);
    }
}