package com.uade.tpo.libreria.tpolibreria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Genero {

    public Genero(){
    }

    //constructor??

    @Id
    private int id;

    @Column
    private String nombre;

    //relaciones

}
