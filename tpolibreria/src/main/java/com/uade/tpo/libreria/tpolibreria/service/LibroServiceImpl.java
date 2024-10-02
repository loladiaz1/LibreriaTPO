package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroRequest;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;
import com.uade.tpo.libreria.tpolibreria.repository.GeneroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private ProductoCarritoService productoCarritoService;

    @Override
    public Page<LibroResponse> getLibros(PageRequest pageable) {
        Page<Libro> libros = libroRepository.findAll(pageable);
        return libros.map(libro -> {
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
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return libroResponse;
        });
    }

    @Override
    public LibroResponse getLibroByIsbn(int isbn) {
        Optional<Libro> existingLibro = libroRepository.findById(isbn);
        LibroResponse libroResponse = new LibroResponse();
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
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
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return libroResponse;
        } else {
            throw new RuntimeException("Libro no encontrado con ISBN: " + isbn);
        }
        
    }
    public Optional<LibroResponse> getLibroByTitulo(String titulo) {
        Optional<Libro> existingLibro = libroRepository.findByTituloContaining(titulo);
        LibroResponse libroResponse = new LibroResponse();
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
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
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Optional.of(libroResponse);
        } else {
            throw new RuntimeException("Libro no encontrado con título: " + titulo);
        }
    }
    @Override
    public Optional<LibroResponse> getLibroByEditorial(String editorial) {
        Optional<Libro> existingLibro = libroRepository.findByEditorialContaining(editorial);
        LibroResponse libroResponse = new LibroResponse();
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
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
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Optional.of(libroResponse);
        } else {
            throw new RuntimeException("Libro no encontrado con editorial: " + editorial);
        }
    }
    @Override
    public Optional<LibroResponse> getLibroByIdioma(String idioma) {
        Optional<Libro> existingLibro = libroRepository.findByIdiomaContaining(idioma);
        LibroResponse libroResponse = new LibroResponse();
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
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
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Optional.of(libroResponse);
        } else {
            throw new RuntimeException("Libro no encontrado con idioma: " + idioma);
        }
    }


    public Optional<LibroResponse> getLibroByAutor(String autor) {
        Optional<Libro> existingLibro = libroRepository.findByAutorContaining(autor);
        LibroResponse libroResponse = new LibroResponse();
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
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
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Optional.of(libroResponse);
        } else {
            throw new RuntimeException("Libro no encontrado con autor: " + autor);
        }
    }




    @Override
    public LibroResponse createLibro(LibroRequest libroRequest) {
        Libro libro = new Libro();
        LibroResponse libroResponse = new LibroResponse();
            libroResponse.setIsbn(libroRequest.getIsbn());
            libro.setIsbn(libroRequest.getIsbn());
            libro.setTitulo(libroRequest.getTitulo());
            libroResponse.setTitulo(libroRequest.getTitulo());
            libro.setPrecio(libroRequest.getPrecio());
            libroResponse.setPrecio(libroRequest.getPrecio());
            libro.setCantPaginas(libroRequest.getCantPaginas());
            libroResponse.setCantPaginas(libroRequest.getCantPaginas());
            libro.setDescripcion(libroRequest.getDescripcion());
            libroResponse.setDescripcion(libroRequest.getDescripcion());
            libro.setStock(libroRequest.getStock());
            libroResponse.setStock(libroRequest.getStock());
            libro.setEditorial(libroRequest.getEditorial());
            libroResponse.setEditorial(libroRequest.getEditorial());
            libro.setEdicion(libroRequest.getEdicion());
            libroResponse.setEdicion(libroRequest.getEdicion());
            libro.setIdioma(libroRequest.getIdioma());
            libroResponse.setIdioma(libroRequest.getIdioma());
            libro.setAutor(libroRequest.getAutor());
            libroResponse.setAutor(libroRequest.getAutor());
            libroResponse.setImage("Sin imagen");

        // Manejo de la asignación del género
        if (libroRequest.getGeneroId() != null) {
            Genero genero = generoRepository.findById(libroRequest.getGeneroId())
                .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + libroRequest.getGeneroId()));
            libro.setGenero(genero);
        }
        libroRepository.save(libro);
        return libroResponse;
    }
    public void deleteLibro(int isbn) {
        //Cuando elimino un libro tengo que eliminar su relacion con productos carrito si es que hay
         Optional<Libro> libro = libroRepository.findById(isbn);
        if (libro.isPresent()) { 
            productoCarritoService.eliminarProductoCarritoByIsbn(isbn);
            libroRepository.deleteById(isbn);
            //si da error, que se mande la clase libro

        } else {
            throw new RuntimeException("Libro no encontrado con ISBN: " + isbn);
        }
    }

    @Override
    public LibroResponse updateLibro(int isbn, LibroRequest libroRequest) {
        Optional<Libro> existingLibro = libroRepository.findById(isbn);
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
            LibroResponse libroResponse = new LibroResponse();
            libro.setTitulo(libroRequest.getTitulo());
            libroResponse.setTitulo(libroRequest.getTitulo());
            libro.setPrecio(libroRequest.getPrecio());
            libroResponse.setPrecio(libroRequest.getPrecio());
            libro.setCantPaginas(libroRequest.getCantPaginas());
            libroResponse.setCantPaginas(libroRequest.getCantPaginas());
            libro.setDescripcion(libroRequest.getDescripcion());
            libroResponse.setDescripcion(libroRequest.getDescripcion());
            libro.setStock(libroRequest.getStock());
            libroResponse.setStock(libroRequest.getStock());
            libro.setEditorial(libroRequest.getEditorial());
            libroResponse.setEditorial(libroRequest.getEditorial());
            libro.setEdicion(libroRequest.getEdicion());
            libroResponse.setEdicion(libroRequest.getEdicion());
            libro.setIdioma(libroRequest.getIdioma());
            libroResponse.setIdioma(libroRequest.getIdioma());
            libro.setAutor(libroRequest.getAutor());
            libroResponse.setAutor(libroRequest.getAutor());
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Actualizar el género si se proporciona
            /*if (libroRequest.getGeneroId() != null) {
                Genero genero = generoRepository.findById(libroRequest.getGeneroId())
                    .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + libroRequest.getGeneroId()));
                libro.setGenero(genero);
            }*/
            libroRepository.save(libro);
            return libroResponse;
        } else {
            throw new RuntimeException("Libro no encontrado con ISBN: " + isbn);
        }
    }
}