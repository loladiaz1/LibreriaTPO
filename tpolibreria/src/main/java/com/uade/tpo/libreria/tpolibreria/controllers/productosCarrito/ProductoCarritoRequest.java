package com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito;

import lombok.Data;

@Data
public class ProductoCarritoRequest {
    private int cantidad;
    private Long isbn;
    private String carrito_mail;
}
