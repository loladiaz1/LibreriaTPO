package com.uade.tpo.libreria.tpolibreria.controllers.libros;

import lombok.Data;
import java.util.List;

@Data
public class LibroRequest {
    private Long isbn;
    private String titulo;
    private double precio;
    private int cantPaginas;
    private String descripcion;
    private int stock;
    private String editorial;
    private int edicion;
    private String idioma;
    private Long generoId; // Usamos el ID del género para asociarlo
    private List<String> autor; // Lista de autores

    private boolean novedad;
    private boolean recomendado;
}