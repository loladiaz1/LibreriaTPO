package com.uade.tpo.libreria.tpolibreria.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;

import jakarta.persistence.EntityNotFoundException;

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

    @Override
    public ProductoCarrito createProductoCarrito(int cantidad, int isbn, String carrito_mail) throws ExcepcionProductoCarritoDuplicado {

        //CAMBIAR LOS EXCEPTIONS
        Carrito carrito = carritoRepository.findById(carrito_mail)
            .orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + carrito_mail));

        ProductoCarrito productoEncontrado = null;
        //busca el productoCarrito y si lo encuentra le actualiza la cantidad
        for (ProductoCarrito productoCarrito : carrito.getProductosCarrito()) {
            if (productoCarrito.getLibro().getIsbn() == isbn) {
                //sumo la cantidad vieja y la cantidad nueva
                productoCarrito.setCantidad(productoCarrito.getCantidad() + cantidad);

                double montoASumar = productoCarrito.getLibro().getPrecio() * cantidad;
                carrito.setTotal(carrito.getTotal() + montoASumar);
                carritoRepository.save(carrito);
                
                productoEncontrado = productoCarrito;
                break;
            }
        }

        //si lo encontro, lo devuelvo y lo guardo en repository
        if (productoEncontrado != null) {
            return ProductoCarritoRepository.save(productoEncontrado);
        }

        //ahora si no se encontro, voy a crear un productoCarrito nuevo:
        Libro libro = libroRepository.findById(isbn)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        
        ProductoCarrito nuevoProductoCarrito = new ProductoCarrito();
        nuevoProductoCarrito.setLibro(libro);  
        nuevoProductoCarrito.setCantidad(cantidad);  
        nuevoProductoCarrito.setCarrito(carrito); 

        double montoASumar = libro.getPrecio() * cantidad;
        carrito.setTotal(carrito.getTotal() + montoASumar);
        carritoRepository.save(carrito);

    return ProductoCarritoRepository.save(nuevoProductoCarrito);
    }

    @Override
    public Optional<ProductoCarrito> getProductoCarritoById(Long ProductoCarritoId) {
        return ProductoCarritoRepository.findById(ProductoCarritoId);
    }

    @Override
    //esta bien el list?, la profe usa page con findall
    public List<ProductoCarrito> getProductosCarritoByMail(String carrito_mail) {
        return ProductoCarritoRepository.findByMail(carrito_mail);
    }

    @Override
    public Optional<Libro> getLibroById(Long ProductoCarritoId) {
        return ProductoCarritoRepository.findLibroByProductoCarritoId(ProductoCarritoId);
    }

    @Override
    public Optional<ProductoCarrito> getProductoCarritoByIsbn(int isbn) {
        return ProductoCarritoRepository.findByIsbn(isbn);
    }

    /* 
    @Override
    public void actualizarProductoCarritoByIsbn(Integer isbn, ProductoCarritoRequest prodCarrRequest) {
        //url: 
        //json:
        //{cantidad: 3}
        
        ProductoCarrito productoCarritoExistente = ProductoCarritoRepository.findByIsbn(isbn)
            .orElseThrow(() -> new EntityNotFoundException("No se encontró el producto en el carrito para el libro con ISBN: " + isbn));
    
        if (prodCarrRequest.getCantidad() > 0){
            //ACA SE VA A CAMBIAR EL TOTAL DEL CARRITO Y LA CANTIDAD DEL LIBRO EN PRODUCTO CARRITO

            
            //1. le resto el precio x cantidad que tenia del libro con la vieja cantidad:
            Carrito carrito = carritoRepository.
            .orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + carrito_mail));

            //va a cambiar la cantidad vieja por la nueva
            productoCarritoExistente.setCantidad(prodCarrRequest.getCantidad());

            //modificar tambien el total del carrito:
            

            double montoNuevo = productoCarritoExistente.getLibro().getPrecio() * prodCarrRequest.getCantidad();


                carrito.setTotal(carrito.getTotal() + montoASumar);
                carritoRepository.save(carrito);

        } else {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        ProductoCarritoRepository.save(productoCarritoExistente);

    }
    */


}
