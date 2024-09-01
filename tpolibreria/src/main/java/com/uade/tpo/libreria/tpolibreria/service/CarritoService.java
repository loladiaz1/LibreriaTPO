package com.uade.tpo.libreria.tpolibreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;

public interface CarritoService {
     public Page<Carrito>getCarritos(PageRequest pageRequest);


    
}
