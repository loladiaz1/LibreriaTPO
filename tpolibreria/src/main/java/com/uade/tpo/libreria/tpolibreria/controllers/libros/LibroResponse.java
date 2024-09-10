package com.uade.tpo.libreria.tpolibreria.controllers.libros;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponse {
    private int isbn;
    private String titulo;
    private double precio;
    private int cantPaginas;
    private String descripcion;
    private int stock;
    private String editorial;
    private int edicion;
    private String idioma;
    private String genero; 
    private List<String> autor;
    private String image;
}
