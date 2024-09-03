package com.uade.tpo.libreria.tpolibreria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El carrito es único por usuario")
public class ExcepcionCarrito extends RuntimeException{

    //chatGPT me dice que es buena practica(?)

    private static final long serialVersionUID = 1L;

    public ExcepcionCarrito() {
        super("El carrito es único por usuario");
    }
    
}
