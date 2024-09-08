package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCarrito;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;


    @Autowired
    private ProductoCarritoRepository ProdCarritoRepository;

     // Devuelve una página de carritos 
    public Page<Carrito> getCarritos(Pageable pageable) {
         return carritoRepository.findAll(pageable);
     }
 
     // Busca un carrito por el nombre de usuario
    public Optional<Carrito> getCarritoById(String mail) {
         return carritoRepository.findById(mail);
     }
    
    public Carrito createCarrito(String mail) throws ExcepcionCarrito{
        Carrito carritoExistente = carritoRepository.findByMail(mail);
        if ((carritoExistente == null)) {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setMail(mail);
            nuevoCarrito.setTotal(0.0);
            return carritoRepository.save(nuevoCarrito);
        }
        throw new ExcepcionCarrito();
    }

    @Override
    public void eliminarCarrito(String mail) {
        Carrito carritoExistente = carritoRepository.findByMail(mail);
        if ((carritoExistente == null)){
            throw new EntityNotFoundException("No se encontró el carrito con el mail: " + mail);
        }
        else{
            carritoRepository.delete(carritoExistente);
        }
    }

    @Override
    public void vaciarCarrito(String mail) {
        Carrito carritoExistente = carritoRepository.findByMail(mail);
        if ((carritoExistente == null)){
            throw new EntityNotFoundException("No se encontró el carrito con el mail: " + mail);
        }
        List<ProductoCarrito> productos = carritoExistente.getProductosCarrito();
        if (!productos.isEmpty()) {
        for (ProductoCarrito productoCarrito : productos) {
            ProdCarritoRepository.delete(productoCarrito);
            
        }
        carritoExistente.setTotal(0.0);
        carritoRepository.save(carritoExistente);
    }
    }  
}
