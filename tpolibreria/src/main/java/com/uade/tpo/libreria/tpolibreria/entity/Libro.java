package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
@Entity
public class Libro {
    
    @Id
    @Column(unique = true)
    private int isbn;

    @Column(nullable = false)
    private String titulo;

    @Column
    private double precio;

    @Column
    private int cantPaginas;

    @Column
    private String descripcion;

    @Column
    private int stock;

    @Column
    private String editorial;

    @Column
    private int edicion;

    @Column
    private String idioma;

    @ManyToOne
    @JoinColumn(name = "genero_id")
    private Genero genero;

    @ElementCollection
    @CollectionTable(name = "autores", joinColumns = @JoinColumn(name = "isbn"))
    @Column(name = "autor")
    private List<String> autor;
}
