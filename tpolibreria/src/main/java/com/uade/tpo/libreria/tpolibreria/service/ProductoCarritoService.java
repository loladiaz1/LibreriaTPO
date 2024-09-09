package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    public void actualizarProductoCarritoByIsbn(int isbn, int cantidad, String mail);

    public String getMailById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: no

    public void eliminarProductoCarritoByIsbnAndMail(int isbn, String carrito_mail);
    
} 
