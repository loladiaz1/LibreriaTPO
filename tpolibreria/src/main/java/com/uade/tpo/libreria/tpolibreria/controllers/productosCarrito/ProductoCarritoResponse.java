package com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoCarritoResponse {
    private long id;
    private LibroResponse libro;
    private int cantidad;
}
