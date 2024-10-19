package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.controllers.generos.GenerosResponse;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionGeneroDuplicado;
import com.uade.tpo.libreria.tpolibreria.repository.GeneroRepository;
import java.util.Base64;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository GeneroRepository;

    @Autowired
    private LibroService LibroService;

    public Page<GenerosResponse> getGeneros(PageRequest pageable) {
        Page<Genero> generos = GeneroRepository.findAll(pageable);
        return generos.map(genero -> {
            GenerosResponse generosResponse = new GenerosResponse();
            generosResponse.setId(genero.getId());
            generosResponse.setNombre(genero.getNombre());
            List<LibroResponse> libros = new  ArrayList<>();
            for (Libro libro  : genero.getLibros()) {
                LibroResponse libroResponse = new LibroResponse();
                libroResponse.setAutor(libro.getAutor());
                libroResponse.setCantPaginas(libro.getCantPaginas());
                libroResponse.setDescripcion(libro.getDescripcion());
                libroResponse.setEdicion(libro.getEdicion());
                libroResponse.setEditorial(libro.getEditorial());
                libroResponse.setGenero(libro.getGenero().getNombre());
                libroResponse.setIdioma(libro.getIdioma());
                libroResponse.setIsbn(libro.getIsbn());
                libroResponse.setPrecio(libro.getPrecio());
                libroResponse.setStock(libro.getStock());
                libroResponse.setTitulo(libro.getTitulo());
                
                String encodedString;
                try {
                    encodedString = Base64.getEncoder()
                        .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                        libroResponse.setImage(encodedString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                libros.add(libroResponse);
            }
            generosResponse.setLibro(libros);
            return generosResponse;
        });
    }

    public Optional<GenerosResponse> getGeneroById(Long GeneroId) {
        Optional<Genero> generos = GeneroRepository.findById(GeneroId);
        return generos.map(genero -> {
            GenerosResponse generosResponse = new GenerosResponse();
            generosResponse.setId(genero.getId());
            generosResponse.setNombre(genero.getNombre());
            List<LibroResponse> libros = new  ArrayList<>();
            for (Libro libro  : genero.getLibros()) {
                LibroResponse libroResponse = new LibroResponse();
                libroResponse.setAutor(libro.getAutor());
                libroResponse.setCantPaginas(libro.getCantPaginas());
                libroResponse.setDescripcion(libro.getDescripcion());
                libroResponse.setEdicion(libro.getEdicion());
                libroResponse.setEditorial(libro.getEditorial());
                libroResponse.setGenero(libro.getGenero().getNombre());
                libroResponse.setIdioma(libro.getIdioma());
                libroResponse.setIsbn(libro.getIsbn());
                libroResponse.setPrecio(libro.getPrecio());
                libroResponse.setStock(libro.getStock());
                libroResponse.setTitulo(libro.getTitulo());
                
                String encodedString;
                try {
                    encodedString = Base64.getEncoder()
                        .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                        libroResponse.setImage(encodedString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                libros.add(libroResponse);
            }
            generosResponse.setLibro(libros);
            return generosResponse;
        });
    }
    

    /* 
    //POST DE LA PROFESORA:
    public Genero createGenero(String nombre) throws ExcepcionGeneroDuplicado {
        List<Genero> generos = GeneroRepository.findByNombre(nombre);
        if (generos.isEmpty())
            return GeneroRepository.save(new Genero());
        throw new ExcepcionGeneroDuplicado();
    }
    */
    
    //NUEVO POST:
    public Genero createGenero(String nombre) throws ExcepcionGeneroDuplicado {
        List<Genero> generos = GeneroRepository.findByNombre(nombre);
        if (generos.isEmpty()) {
            Genero nuevoGenero = new Genero();
            nuevoGenero.setNombre(nombre);
            return GeneroRepository.save(nuevoGenero);
        }
        throw new ExcepcionGeneroDuplicado();
    }
    @Override
    public Genero updateGenero(Long id, String nombre) throws ExcepcionGeneroDuplicado {
        Optional<Genero> generoOptional = GeneroRepository.findById(id);
        if (generoOptional.isPresent()) {
            Genero genero = generoOptional.get();
            List<Genero> generos = GeneroRepository.findByNombre(nombre);
            if (generos.isEmpty() || generos.get(0).getId().equals(id)) {
                genero.setNombre(nombre);
                return GeneroRepository.save(genero);
            }
            throw new ExcepcionGeneroDuplicado();
        }
        throw new RuntimeException("Género no encontrado con ID: " + id);
    }

    @Override
    public void deleteGenero(Long id) {
        Optional<Genero> genero = GeneroRepository.findById(id);

        if (genero.isPresent()) {
            
            List<Libro> libros = genero.get().getLibros();
            for (Libro lib : libros) {
                LibroService.deleteLibro(lib.getIsbn());
            }
             
            GeneroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Género no encontrado con ID: " + id);
        }
    }


}
