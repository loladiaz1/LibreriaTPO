package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;

@Service
public class ProductoCarritoServiceImpl implements ProductoCarritoService{
    @Autowired
    private ProductoCarritoRepository ProductoCarritoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Override
    public Page<ProductoCarrito> getProductosCarrito(PageRequest pageRequest) {
        return ProductoCarritoRepository.findAll(pageRequest);
    }

    public Optional<Integer> getCantidadById(Long productoCarritoId) {
        return ProductoCarritoRepository.findCantidadById(productoCarritoId);
    }

    /*
    public ProductoCarrito createProductoCarrito(int cantidad, int isbn, String carritoMail) throws ExcepcionProductoCarritoDuplicado {
        // Verificar si el ProductoCarrito ya existe en el carrito
        Carrito carrito = carritoRepository.findById(carritoMail)
            .orElseThrow(() -> new RuntimeException("Carrito no encontrado con mail: " + carritoMail));
        
        // Verificar si el libro existe
        Libro libro = libroRepository.findById(isbn)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));

        // Verificar si el productoCarrito ya existe en el carrito
        boolean productoExistente = carrito.getProductosCarrito().stream()
            .anyMatch(pc -> pc.getLibro().equals(libro));

        if (productoExistente) {
            throw new ExcepcionProductoCarritoDuplicado("El producto ya está en el carrito.");
        }

        // Crear una nueva instancia de ProductoCarrito
        ProductoCarrito nuevoProductoCarrito = new ProductoCarrito();
        nuevoProductoCarrito.setCantidad(cantidad);
        nuevoProductoCarrito.setLibro(libro);
        nuevoProductoCarrito.setCarrito(carrito);

        // Agregar el ProductoCarrito al carrito
        carrito.getProductosCarrito().add(nuevoProductoCarrito);

        // Actualizar el total del carrito
        carrito.calcularYActualizarPrecioTotal();

        // Guardar el ProductoCarrito y el Carrito
        productoCarritoRepository.save(nuevoProductoCarrito);
        carritoRepository.save(carrito);

        return nuevoProductoCarrito;
    }

    
     */
    

    @Override
    public ProductoCarrito createProductoCarrito(int cantidad, int isbn, String carrito_mail) throws ExcepcionProductoCarritoDuplicado {
        //Optional<ProductoCarrito> productoCarritoExistente = ProductoCarritoRepository.findById(id);
        //if (productoCarritoExistente.isPresent()) {
        //throw new ExcepcionProductoCarritoDuplicado();
        //}
    
        ProductoCarrito nuevoProductoCarrito = new ProductoCarrito();
        nuevoProductoCarrito.setCantidad(cantidad);

        //Relaciones
        Libro libro = libroRepository.findById(isbn)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));
        nuevoProductoCarrito.setLibro(libro);

        Carrito carrito = carritoRepository.findById(carrito_mail)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con mail: " + carrito_mail));
        nuevoProductoCarrito.setCarrito(carrito);

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
