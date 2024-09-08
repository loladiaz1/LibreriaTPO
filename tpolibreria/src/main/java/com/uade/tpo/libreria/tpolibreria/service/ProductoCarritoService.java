package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;

public interface ProductoCarritoService {
    public Page<ProductoCarrito> getProductosCarrito(PageRequest pageRequest);
    
    public Optional<Integer> getCantidadById(Long ProductoCarritoId);
    
    public Optional<ProductoCarrito> getProductoCarritoById(Long ProductoCarritoId);

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
    //CREO QUE VA A SER MEJOR UN PATCH(investigar)

    public String getMailById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: no

    public void eliminarProductoCarritoByIsbnAndMail(ProductoCarritoRequest prodCarrRequest);
    //^^^si lo hago de esta forma, el usuario tiene que escribir el isbn en la url
    //admin: no, usuario: si

    //otra forma(que el usuario escriba el isbn en el json):
    //public void eliminarProductoCarrito(ProductoCarritoRequest prodCarrRequest);

} 
