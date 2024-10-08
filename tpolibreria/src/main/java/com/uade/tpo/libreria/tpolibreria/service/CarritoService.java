package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCarrito;

public interface CarritoService {

    Page<Carrito> getCarritos(Pageable pageable);

    Optional<Carrito> getCarritoById(String mail);
     
    public Carrito createCarrito(String mail) throws ExcepcionCarrito;

    public void eliminarCarrito(String mail); //cuando se elimina la cuenta
    
    public void vaciarCarrito(String mail); //cuando se crea orden, se van a eliminar los productosCarrito
}
