package com.uade.tpo.libreria.tpolibreria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.libreria.tpolibreria.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    @Query(value = "select g from Genero g where g.nombre = ?1")
    List<Genero> findByNombre(String nombre);
}

