package com.uade.tpo.libreria.tpolibreria.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre_usuario;

    @Column(nullable = false, unique = true)
    private String mail;

    @Column(nullable = false)
    private String contraseña;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String direccion;

    @Column
    private int CP;

    @Column
    private String rol;

    @OneToOne
    @JoinColumn(name = "carrito_id", unique = true)
    @JsonManagedReference
    private Carrito carrito;
}
