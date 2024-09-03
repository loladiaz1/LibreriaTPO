package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Carrito {

    @Id
    @Column
    private String nombreUsuario;

    @Column
    private double precio;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoCarrito> productos;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    @JsonBackReference
    private Usuario usuario;
}




