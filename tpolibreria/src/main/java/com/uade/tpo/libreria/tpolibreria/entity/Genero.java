package com.uade.tpo.libreria.tpolibreria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import lombok.Data;

@Data
@Entity
public class Genero {

    public Genero(){
    }

    //constructor??

    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    
    //relacion con generos
    //muchos generos puede tener un libro
    @OneToMany(mappedBy = "genero")
    private Set<Libro> libros;
    
}
