package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.HashMap;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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

    //relacion con usuarios
    //un usuario tiene un carrito UNICO !!
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuarios usuario;

    //relacion con libros
    //muchos libros pueden estar en muchos carritos
    @ManyToMany
    @ElementCollection
    private HashMap<Libros, Integer> librosEnCarritos= new HashMap<>();
    

    
}




