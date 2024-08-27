package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
@Entity
public class Libros {
    
    @Id
    private int isbn;

    @Column
    private String titulo;
    @Column
    private double precio;
    @Column
    private int cantPaginas;
    @Column
    private String descripcion;
    @Column
    private List<String> autor;
    @Column
    private int stock;
    @Column
    private String editorial;
    @Column
    private int edicion;
    @Column
    private String idioma;
    @Column
    private List<String> genero;
    
    /* 
    @ManyToOne
    @JoinColumn(name = "genero_libro")

    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    */
    
}
