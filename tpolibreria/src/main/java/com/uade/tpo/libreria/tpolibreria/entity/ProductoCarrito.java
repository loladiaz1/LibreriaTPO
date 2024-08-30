package com.uade.tpo.libreria.tpolibreria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ProductoCarrito {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Libro libro;

    @Column
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "carrito_nombreUsuario")
    private Carrito carrito;
}
