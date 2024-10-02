package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.entity.Image;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.repository.ImageRepository;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public Image create(Image image, Long isbn) {
        Libro libro = libroRepository.findById(isbn)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con el isbn: " + isbn));
        image.setLibro(libro);
        libro.setImage(image);
        return imageRepository.save(image);
    }

    @Override
    public Image viewById(long id) {
        return imageRepository.findById(id).get();
    }
}
