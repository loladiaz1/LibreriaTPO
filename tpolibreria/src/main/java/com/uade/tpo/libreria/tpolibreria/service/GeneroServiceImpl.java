package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCategoriaDuplicada;
import com.uade.tpo.libreria.tpolibreria.repository.GeneroRepository;

@Service
public class GeneroServiceImpl implements GeneroService {

    @Autowired
    private GeneroRepository GeneroRepository;

    public Page<Genero> getGeneros(PageRequest pageable) {
        return GeneroRepository.findAll(pageable);
    }

    public Optional<Genero> getGeneroById(Long GeneroId) {
        return GeneroRepository.findById(GeneroId);
    }

    public Genero createGenero(String nombre) throws ExcepcionCategoriaDuplicada {
        List<Genero> generos = GeneroRepository.findByNombre(nombre);
        if (generos.isEmpty())
            return GeneroRepository.save(new Genero());
        throw new ExcepcionCategoriaDuplicada();
    }

}
