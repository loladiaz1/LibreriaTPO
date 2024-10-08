
package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Genero;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionGeneroDuplicado;

public interface GeneroService {
    public Page<Genero> getGeneros(PageRequest pageRequest);
    public Optional<Genero> getGeneroById(Long GeneroId);
    public Genero createGenero(String nombre) throws ExcepcionGeneroDuplicado;
    public Genero updateGenero(Long id, String nombre) throws ExcepcionGeneroDuplicado;
    public void deleteGenero(Long id);
}