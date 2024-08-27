package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Carrito {

    @Id
    private String nombreUsuario;
    @Column
    private int cantLibros;
    @Column
    private double precio;
    @Column
    private HashMap<Libros,Integer> productos; 
    
}




