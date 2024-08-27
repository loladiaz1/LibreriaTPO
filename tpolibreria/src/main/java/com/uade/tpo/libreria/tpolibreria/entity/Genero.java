package com.uade.tpo.libreria.tpolibreria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Genero {

    public Genero(){
    }

    //constructor??

    @Id
    private Long id;

    @Column
    private String nombre;

    /* 
    
    @OneToMany(mappedBy = "genero")
    private Libros libros;

    */

}
