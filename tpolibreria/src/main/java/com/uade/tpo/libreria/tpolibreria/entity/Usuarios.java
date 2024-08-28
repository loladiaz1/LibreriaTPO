package com.uade.tpo.libreria.tpolibreria.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuarios {

    public Usuarios(){
    }

    @Id
    private int id;

    @Column
    private String nombre_usuario;

    @Column
    private String mail;

    @Column
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

    @Column
    private ArrayList<Libros> wishList;

}
