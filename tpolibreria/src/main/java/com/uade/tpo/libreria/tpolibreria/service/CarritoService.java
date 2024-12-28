package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uade.tpo.libreria.tpolibreria.controllers.carritos.CarritoResponse;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCarrito;

public interface CarritoService {

    Page<CarritoResponse> getCarritos(Pageable pageable);

    Optional<CarritoResponse> getCarritoById(String mail);
    
    public CarritoResponse createCarrito(String mail) throws ExcepcionCarrito;

    public void eliminarCarrito(String mail); //cuando se elimina la cuenta
    
    public void vaciarCarrito(String mail); //cuando se crea orden, se van a eliminar los productosCarrito
}
