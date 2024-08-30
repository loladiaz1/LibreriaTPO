package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Carrito {

    @Id
    private String nombreUsuario;

    @Column
    private double precio;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoCarrito> productos;

    //relacion con usuarios
    //un usuario tiene un carrito UNICO !!
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    //relacion con libros
    //muchos libros pueden estar en muchos carritos
/* 
    @ElementCollection
    @CollectionTable(name = "carrito_libros")
    @MapKeyJoinColumn(name = "libro_id")
    @Column(name = "cantidad")
    private Map<Libro, Integer> librosEnCarritos = new HashMap<>();
    */
}




