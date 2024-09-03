package com.uade.tpo.libreria.tpolibreria.entity.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
    private Long id;
    private String nombre_usuario;
    private String mail;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String direccion;
    private int CP;
    private String rol;
}
