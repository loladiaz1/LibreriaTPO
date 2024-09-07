package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;

public interface ProductoCarritoService {
    public Page<ProductoCarrito> getProductosCarrito(PageRequest pageRequest);
    //^^^ admin: si, usuario: no
    
    public Optional<Integer> getCantidadById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: si

    public Optional<ProductoCarrito> getProductoCarritoById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: no

    public ProductoCarrito createProductoCarrito(int cantidad, int isbn, String carrito_mail) throws ExcepcionProductoCarritoDuplicado;
    //^^^ admin: no, usuario: si

    public List<ProductoCarrito> getProductosCarritoByMail(String carrito_mail); //se van a mostrar los ProductosCarrito de un usuario
    //^^^ admin: si, usuario: solo permitirle que ponga su mail

    public Optional<Libro> getLibroByProductoCarritoId(Long ProductoCarritoId);
    //^^^ solo para el admin
    
    /*
    posibles:
    - getProductosCarritoByIsbn(int isbn)
    */

} 
