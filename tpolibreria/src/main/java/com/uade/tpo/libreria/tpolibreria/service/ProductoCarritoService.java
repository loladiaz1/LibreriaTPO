package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;

public interface ProductoCarritoService {
    public Page<ProductoCarrito> getProductosCarrito(PageRequest pageRequest);
    
    public Optional<Integer> getCantidadById(Long ProductoCarritoId);
    
    public Optional<ProductoCarrito> getProductoCarritoById(Long ProductoCarritoId);

    public ProductoCarrito createProductoCarrito(int cantidad, int isbn, String carrito_mail) throws ExcepcionProductoCarritoDuplicado;
    
    /*
    posibles:
    - getLibroByProductoCarritoId(Long ProductoCarritoId) (obtendrias el ISBN)
    */
} 
