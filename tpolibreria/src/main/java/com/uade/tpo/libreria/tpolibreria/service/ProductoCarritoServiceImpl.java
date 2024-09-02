package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;

@Service
public class ProductoCarritoServiceImpl implements ProductoCarritoService{
    @Autowired
    private ProductoCarritoRepository ProductoCarritoRepository;

    @Override
    public Page<ProductoCarrito> getProductosCarrito(PageRequest pageRequest) {
        return ProductoCarritoRepository.findAll(pageRequest);
    }

    public Optional<Integer> getCantidadById(Long productoCarritoId) {
        return ProductoCarritoRepository.findCantidadById(productoCarritoId);
    }

    @Override
    public ProductoCarrito createProductoCarrito(Long id, int cantidad) throws ExcepcionProductoCarritoDuplicado {
        Optional<ProductoCarrito> productoCarritoExistente = ProductoCarritoRepository.findById(id);
    
        if (productoCarritoExistente.isPresent()) {
        throw new ExcepcionProductoCarritoDuplicado();
        }
    
        ProductoCarrito nuevoProductoCarrito = new ProductoCarrito();
        nuevoProductoCarrito.setId(id);
        nuevoProductoCarrito.setCantidad(cantidad);
    
    return ProductoCarritoRepository.save(nuevoProductoCarrito);
    }

    @Override
    public Optional<ProductoCarrito> getProductoCarritoById(Long ProductoCarritoId) {
        return ProductoCarritoRepository.findById(ProductoCarritoId);
    }

    /*
    public Page<Genero> getGeneros(PageRequest pageable) {
        return GeneroRepository.findAll(pageRequest);
    }

    public Optional<Genero> getGeneroById(Long GeneroId) {
        return GeneroRepository.findById(GeneroId);
    }
     * 
     * 
     */
}
