package com.uade.tpo.libreria.tpolibreria.controllers.generos;

import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerosResponse {
    private Long id;
    private String nombre;
    private List<LibroResponse> libro;
}
