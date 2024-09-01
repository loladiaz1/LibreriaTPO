package com.uade.tpo.libreria.tpolibreria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "La categoria que se intenta agregar esta duplicada")
public class ExcepcionProductoCDuplicado extends Exception{
    
}
