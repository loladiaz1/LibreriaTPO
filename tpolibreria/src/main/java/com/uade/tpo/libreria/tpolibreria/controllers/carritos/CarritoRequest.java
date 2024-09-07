package com.uade.tpo.libreria.tpolibreria.controllers.carritos;

import lombok.Data;

@Data
public class CarritoRequest {
    private String mail;
    
    //private double precio; --> no necesito pasarle en el post el precio/total, lo voy a inicializar en 0 y cuando tenga productosCarrito lo voy a actualizar
    
}
