package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoResponse;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;

public interface ProductoCarritoService {
    public Page<ProductoCarritoResponse> getProductosCarrito(PageRequest pageRequest);
    
    public Optional<Integer> getCantidadById(Long ProductoCarritoId);
    
    public Optional<ProductoCarritoResponse> getProductoCarritoById(Long ProductoCarritoId);

    public ProductoCarritoResponse createProductoCarrito(int cantidad, Long isbn, String carrito_mail) throws ExcepcionProductoCarritoDuplicado;
    //^^^ admin: no, usuario: si

    public List<ProductoCarritoResponse> getProductosCarritoByMail(String carrito_mail); //se van a mostrar los ProductosCarrito de un usuario
    //^^^ admin: si, usuario: solo permitirle que ponga su mail

    public LibroResponse getLibroById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: no
    
    //para eliminar los productos carrito con el isbn indicado sin que me importe de quien es --> para eliminar libro
    
    public List<ProductoCarritoResponse> getProductosCarritoByIsbn(Long isbn);
    //^^^ admin: si, usuario: no
    

    public void actualizarProductoCarritoByIsbn(Long isbn, int cantidad, String mail);

    public String getMailById(Long ProductoCarritoId);
    //^^^ admin: si, usuario: no

    public void eliminarProductoCarritoByIsbnAndMail(Long isbn, String carrito_mail);
    
    public void eliminarProductoCarritoByIsbn(Long isbn);
} 
