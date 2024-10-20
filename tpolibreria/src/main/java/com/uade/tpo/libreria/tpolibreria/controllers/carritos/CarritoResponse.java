package com.uade.tpo.libreria.tpolibreria.controllers.carritos;

import java.util.List;

import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponse {
    private String mail;
    private Double total;
    private List<ProductoCarritoResponse> productoCarrito;
}
