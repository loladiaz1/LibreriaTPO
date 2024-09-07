package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;

public interface ProductoCarritoService {
    //se usa page porque en la implementacion se usa el findAll que es parte de JPA, y como parametro se le pasa un pageable
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

    public Optional<Libro> getLibroById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: no
    
    public Optional<ProductoCarrito> getProductoCarritoByIsbn(int isbn);
    //^^^ admin: si, usuario: no

    public void actualizarProductoCarritoByIsbn(Integer isbn, ProductoCarritoRequest prodCarrRequest);
    //^^^ admin: no, usuario: si (el usuario solo va a modificar la cantidad, porque no quiero que cambie el mail ni el libro)
    
    //CUANDO MODIFIQUE LA CANTIDAD SE TIENE QUE MODIFICAR EL TOTAL DE CARRITO!!!!!!    
    //CREO QUE VA A SER MEJOR UN PATCH
} 
