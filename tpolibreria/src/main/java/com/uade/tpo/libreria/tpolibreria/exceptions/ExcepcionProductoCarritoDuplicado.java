package com.uade.tpo.libreria.tpolibreria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El producto que se intenta agregar al carrito esta duplicado.")
public class ExcepcionProductoCarritoDuplicado extends Exception{
    
}
