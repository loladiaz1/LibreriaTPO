package com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito;

import lombok.Data;

@Data
public class ProductoCarritoRequest {
    private int cantidad;
    private int isbn;
    private String carrito_mail;
}
