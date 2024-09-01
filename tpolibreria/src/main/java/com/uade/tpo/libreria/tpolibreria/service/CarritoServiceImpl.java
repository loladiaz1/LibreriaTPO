package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;

@Service
public class CarritoServiceImpl implements CarritoService{

    @Autowired
    private CarritoRepository carritoRepository;

     // Devuelve una página de carritos 
     @Override
     public Page<Carrito> getCarritos(Pageable pageable) {
         return carritoRepository.findAll(pageable);
     }
 
     // Busca un carrito por el nombre de usuario
     @Override
     public Optional<Carrito> getCarritoById(String nombreUsuario) {
         return carritoRepository.findById(nombreUsuario);
     }

    
}
