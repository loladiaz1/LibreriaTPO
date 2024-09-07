package com.uade.tpo.libreria.tpolibreria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "La GiftCard no fue encontrada con el ID proporcionado")
public class ExcepcionGiftCard extends Exception {

    private static final long serialVersionUID = 1L; // Para serialización

    public ExcepcionGiftCard(String mensaje) {
        super(mensaje);
    }


    
}
