package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
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

    @Override
    public ProductoCarrito createProductoCarrito(int cantidad, Long isbn, String carrito_mail) throws ExcepcionProductoCarritoDuplicado {
         //CAMBIAR LOS EXCEPTIONS
        Carrito carrito = carritoRepository.findById(carrito_mail)
            .orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + carrito_mail));        
        
        ProductoCarrito productoEncontrado = null;
        //busca el productoCarrito y si lo encuentra le actualiza la cantidad
        for (ProductoCarrito productoCarrito : carrito.getProductosCarrito()) {
            if (productoCarrito.getLibro().getIsbn() == isbn) {
                //EXCEPCION POR STOCK
                Libro libro = productoCarrito.getLibro();
                if (cantidad + productoCarrito.getCantidad()> libro.getStock()) {
                    throw new RuntimeException("No hay stock suficiente para el libro: " + libro.getTitulo());
                }
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

        if (cantidad > libro.getStock()) {
            throw new RuntimeException("No hay stock suficiente para el libro: " + libro.getTitulo());
        }
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
    public List<ProductoCarrito> getProductosCarritoByMail(String carrito_mail) {
        return ProductoCarritoRepository.findByMail(carrito_mail);
    }

    @Override
    public Optional<Libro> getLibroById(Long ProductoCarritoId) {
        return ProductoCarritoRepository.findLibroByProductoCarritoId(ProductoCarritoId);
    }

    
    @Override
    public List<ProductoCarrito> getProductosCarritoByIsbn(Long isbn) {
        return ProductoCarritoRepository.findByIsbn(isbn);
    }
    
    @Override
    public void actualizarProductoCarritoByIsbn(Long isbn, int cantidad, String mail) {
        ProductoCarrito productoCarrito = ProductoCarritoRepository.findByIsbnAndCarritoMail(isbn, mail)
        .orElseThrow(() -> new RuntimeException("No se encontró el producto con ISBN: " + isbn + " en el carrito con mail: " + mail));
        
        Libro libro = productoCarrito.getLibro();
        //cantidad + productoCarrito.getCantidad() --> es asi porque yo antes le puse cantidad
        if (cantidad + productoCarrito.getCantidad()> libro.getStock()) {
            throw new RuntimeException("No hay stock suficiente para el libro: " + libro.getTitulo());
        }

        double montoARestar = productoCarrito.getLibro().getPrecio() * productoCarrito.getCantidad();
        Carrito carrito = productoCarrito.getCarrito();
        carrito.setTotal(carrito.getTotal() - montoARestar);

        if (cantidad > 0) {
            productoCarrito.setCantidad(cantidad);
            double montoNuevo = productoCarrito.getLibro().getPrecio() * productoCarrito.getCantidad();
            carrito.setTotal(carrito.getTotal() + montoNuevo);
            ProductoCarritoRepository.save(productoCarrito); 
        } else {
            ProductoCarritoRepository.delete(productoCarrito); // elimina el producto si la cantidad es 0 o menor
        }
        carritoRepository.save(carrito);
    }

    @Override
    public String getMailById(Long ProductoCarritoId) {
        return ProductoCarritoRepository.findMailById(ProductoCarritoId);
    }

    public void eliminarProductoCarritoByIsbnAndMail(Long isbn, String carritoMail){
        ProductoCarrito productoAEliminar = ProductoCarritoRepository.findByIsbnAndCarritoMail(isbn, carritoMail)
        .orElseThrow(() -> new RuntimeException("No se encontró el producto con ISBN: " + isbn + " en el carrito con mail: " + carritoMail));

        double montoARestar = productoAEliminar.getLibro().getPrecio() * productoAEliminar.getCantidad();
        Carrito carrito = productoAEliminar.getCarrito();
        carrito.setTotal(carrito.getTotal() - montoARestar);
        carritoRepository.save(carrito);

        ProductoCarritoRepository.delete(productoAEliminar);
    }
    
    public void eliminarProductoCarritoByIsbn(Long isbn){
        //es lo mismo que productoCarritorepository.findByIsbn()
        List<ProductoCarrito> lista = getProductosCarritoByIsbn(isbn);
        for (ProductoCarrito prodcarr : lista) {
            double montoARestar = prodcarr.getLibro().getPrecio() * prodcarr.getCantidad();
            Carrito carrito = prodcarr.getCarrito();
            carrito.setTotal(carrito.getTotal() - montoARestar);
            carritoRepository.save(carrito);
            
            ProductoCarritoRepository.delete(prodcarr);
        }


    }
}
