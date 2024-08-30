package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Usuario {

    public Usuario(){
    }

    @Id
    private int id;

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

    @OneToOne(mappedBy = "usuario")
    private Carrito carrito;

}
