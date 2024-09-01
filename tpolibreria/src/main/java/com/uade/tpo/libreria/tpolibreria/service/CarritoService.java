package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;


public interface CarritoService {
     Page<Carrito> getCarritos(Pageable pageable);
     Optional<Carrito> getCarritoById(String nombreUsuario);


    
}
