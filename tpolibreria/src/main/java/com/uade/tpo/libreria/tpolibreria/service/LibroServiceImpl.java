package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.repository.LibrosRepository;

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
    private LibrosRepository libroRepository;

    @Override
    public Page<Libro> getLibros(PageRequest pageable, String titulo, String autor) {
        List<Libro> libros;
        if (titulo != null && !titulo.isEmpty()) {
            libros = libroRepository.findByTituloContaining(titulo);
        } else if (autor != null && !autor.isEmpty()) {
            libros = libroRepository.findByAutorContaining(autor);
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
    public Libro createLibro(Libro libro) {
        return libroRepository.save(libro);
    }
}

