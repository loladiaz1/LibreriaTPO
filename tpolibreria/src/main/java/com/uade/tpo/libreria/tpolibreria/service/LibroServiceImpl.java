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
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private ProductoCarritoService productoCarritoService;

    @Autowired
    private ImageService imageService;

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

            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return libroResponse;
        });
    }

    @Override
    public LibroResponse getLibroByIsbn(Long isbn) {
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

            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return libroResponse;
        } else {
            throw new RuntimeException("Libro no encontrado con ISBN: " + isbn);
        }
        
    }
    public List<LibroResponse> getLibroByTitulo(String titulo) {
        List<Libro> libros = libroRepository.findByTituloContaining(titulo);
        return libros.stream().map(libro -> {
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

            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return libroResponse;
        }).collect(Collectors.toList());
    }
    @Override
    public List<LibroResponse> getLibroByEditorial(String editorial) {
        List<Libro> libros = libroRepository.findByEditorialContaining(editorial);
        return libros.stream().map(libro -> {
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

            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return libroResponse;
        }).collect(Collectors.toList());
    }
    @Override
    public List<LibroResponse> getLibroByIdioma(String idioma) {
        List<Libro> libros = libroRepository.findByIdiomaContaining(idioma);
        return libros.stream().map(libro -> {
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

            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return libroResponse;
        }).collect(Collectors.toList());
    }


    public List<LibroResponse> getLibroByAutor(String autor) {
        List<Libro> libros = libroRepository.findByAutorContaining(autor);
        return libros.stream().map(libro -> {
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

            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return libroResponse;
        }).collect(Collectors.toList());
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

            libro.setNovedad(libroRequest.isNovedad());
            libro.setRecomendado(libroRequest.isRecomendado());
            libroResponse.setNovedad(libroRequest.isNovedad());
            libroResponse.setRecomendado(libroRequest.isRecomendado());

        // Manejo de la asignación del género
        if (libroRequest.getGeneroId() != null) {
            Genero genero = generoRepository.findById(libroRequest.getGeneroId())
                .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + libroRequest.getGeneroId()));
            libro.setGenero(genero);
        }
        libroRepository.save(libro);
        return libroResponse;
    }

    @Transactional
    public void deleteLibro(Long isbn) {
        Optional<Libro> libro = libroRepository.findById(isbn);
        if (libro.isPresent()) { 
            productoCarritoService.eliminarProductoCarritoByIsbn(isbn);
            if (libro.get().getImage() != null){
                imageService.deleteImage(libro.get().getImage().getId());
            }
            else {
                libroRepository.deleteByIsbn(isbn);
            }
        } else {
            throw new RuntimeException("Libro no encontrado con ISBN: " + isbn);
        }
    }

    @Override
    public LibroResponse updateLibro(Long isbn, LibroRequest libroRequest) {
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
            
            libro.setNovedad(libroRequest.isNovedad());
            libro.setRecomendado(libroRequest.isRecomendado());
            libroResponse.setNovedad(libroRequest.isNovedad());
            libroResponse.setRecomendado(libroRequest.isRecomendado());
            
            String encodedString;
            try {
                encodedString = Base64.getEncoder()
                    .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                    libroResponse.setImage(encodedString);
            } catch (Exception e) {
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

    @Override
    public Page<LibroResponse> getLibrosByGeneroId(Long id, PageRequest pageable) {
        // Buscar libros por género usando el ID de género
        Page<Libro> libros = libroRepository.findByGeneroId(id, pageable); 

        return libros.map(libro -> {
            LibroResponse libroResponse = new LibroResponse();
            libroResponse.setAutor(libro.getAutor());
            libroResponse.setCantPaginas(libro.getCantPaginas());
            libroResponse.setDescripcion(libro.getDescripcion());
            libroResponse.setEdicion(libro.getEdicion());
            libroResponse.setEditorial(libro.getEditorial());
            libroResponse.setGenero(libro.getGenero().getNombre()); // Nombre del género
            libroResponse.setIdioma(libro.getIdioma());
            libroResponse.setIsbn(libro.getIsbn());
            libroResponse.setPrecio(libro.getPrecio());
            libroResponse.setStock(libro.getStock());
            libroResponse.setTitulo(libro.getTitulo());
            libroResponse.setNovedad(libro.isNovedad());
            libroResponse.setRecomendado(libro.isRecomendado());

            // Manejo de imagen (Base64)
            String encodedString = null;
            try {
                if (libro.getImage() != null && libro.getImage().getImage() != null) {
                    encodedString = Base64.getEncoder()
                        .encodeToString(libro.getImage().getImage().getBytes(1, (int) libro.getImage().getImage().length()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            libroResponse.setImage(encodedString);
            
            return libroResponse;
        });
    }


}