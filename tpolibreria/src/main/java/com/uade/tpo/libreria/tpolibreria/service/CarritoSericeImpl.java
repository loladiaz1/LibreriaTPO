package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCarrito;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;


@Service
public class CarritoSericeImpl implements CarritoService {
     @Autowired
    private CarritoRepository carritoRepository;

     // Devuelve una página de carritos 
    public Page<Carrito> getCarritos(Pageable pageable) {
         return carritoRepository.findAll(pageable);
     }
 
     // Busca un carrito por el nombre de usuario
    public Optional<Carrito> getCarritoById(String nombreUsuario) {
         return carritoRepository.findById(nombreUsuario);
     }
    
    public Carrito createCarrito(String nombreUsuario, double precio) throws ExcepcionCarrito{
        List<Carrito> carritos = carritoRepository.findByNombreUsuario(nombreUsuario);
        if (carritos.isEmpty()) {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setNombreUsuario(nombreUsuario);
            nuevoCarrito.setPrecio(precio);
            return carritoRepository.save(nuevoCarrito);
        }
        throw new ExcepcionCarrito();


    }
    

    
}
