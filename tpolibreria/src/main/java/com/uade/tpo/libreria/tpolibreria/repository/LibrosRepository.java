package com.uade.tpo.libreria.tpolibreria.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;

@Repository
public interface LibrosRepository extends JpaRepository<Libro, Integer> {

    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %?1%")
    List<Libro> findByTituloContaining(String titulo);

    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE a LIKE %?1%")
    List<Libro> findByAutorContaining(String autor);
}

