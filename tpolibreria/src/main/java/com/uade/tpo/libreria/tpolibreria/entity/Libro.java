package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Data
@Entity

public class Libro {

    public Libro(){
    }
    
    @Id
    @Column(unique = true)
    private Long isbn;

    @Column(nullable = false)
    private String titulo;

    @Column
    private double precio;

    @Column
    private int cantPaginas;

    @Column(length = 5000)
    @Lob
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
    @JsonBackReference
    private Genero genero;

    @ElementCollection
    @CollectionTable(name = "autores", joinColumns = @JoinColumn(name = "isbn"))
    @Column(name = "autor")
    private List<String> autor;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    @JsonManagedReference
    private Image image;

    @Column
    private boolean novedad;

    @Column
    private boolean recomendado;

}
