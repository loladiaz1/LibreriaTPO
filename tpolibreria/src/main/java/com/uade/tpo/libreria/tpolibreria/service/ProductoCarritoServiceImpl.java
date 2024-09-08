package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoRequest;
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
    
    @Override
    public void actualizarProductoCarritoByIsbn(Integer isbn, ProductoCarritoRequest prodCarrRequest) {
        Carrito carrito = carritoRepository.findById(prodCarrRequest.getCarrito_mail())
            .orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + prodCarrRequest.getCarrito_mail()));
        
        for (ProductoCarrito productoCarrito : carrito.getProductosCarrito()) {
            if (productoCarrito.getLibro().getIsbn() == isbn) {
                if (prodCarrRequest.getCantidad() > 0){
                    double montoARestar = productoCarrito.getLibro().getPrecio() * productoCarrito.getCantidad(); //esta es la cantidad vieja
                    carrito.setTotal(carrito.getTotal() - montoARestar);
                    
                    productoCarrito.setCantidad(prodCarrRequest.getCantidad()); //el request tiene la cantidad nueva(el put que hizo el usuario)
                    ProductoCarritoRepository.save(productoCarrito);
                    double montoNuevo = productoCarrito.getLibro().getPrecio() * prodCarrRequest.getCantidad();
                    carrito.setTotal(carrito.getTotal() + montoNuevo);
                    carritoRepository.save(carrito);
                } else{
                    ProductoCarritoRepository.delete(productoCarrito);
                }

            }
        }
    }

    @Override
    public String getMailById(Long ProductoCarritoId) {
        return ProductoCarritoRepository.findMailById(ProductoCarritoId);
    }

    public void eliminarProductoCarritoByIsbnAndMail(ProductoCarritoRequest prodCarrRequest){
        Carrito carrito = carritoRepository.findById(prodCarrRequest.getCarrito_mail())
            .orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + prodCarrRequest.getCarrito_mail()));

        for (ProductoCarrito productoCarrito : carrito.getProductosCarrito()) {
            if (productoCarrito.getLibro().getIsbn() == prodCarrRequest.getIsbn()) {
                double montoARestar = productoCarrito.getLibro().getPrecio() * productoCarrito.getCantidad();
                carrito.setTotal(carrito.getTotal() - montoARestar);
                carritoRepository.save(carrito);
                ProductoCarritoRepository.delete(productoCarrito);
            }
    }
    }
    
}
