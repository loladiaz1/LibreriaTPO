package com.uade.tpo.libreria.tpolibreria.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.controllers.carritos.CarritoResponse;
import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito.ProductoCarritoResponse;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCarrito;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.ProductoCarritoRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoCarritoRepository ProdCarritoRepository;


     // Devuelve una página de carritos 
    public Page<CarritoResponse> getCarritos(Pageable pageable) {
        Page<Carrito> carritos = carritoRepository.findAll(pageable);
         return carritos.map(carrito ->{
            CarritoResponse carritoResponse = new CarritoResponse();
            carritoResponse.setMail(carrito.getMail());
            carritoResponse.setTotal(carrito.getTotal());
            List<ProductoCarritoResponse> respuesta = new  ArrayList<>();
            for (ProductoCarrito producto : carrito.getProductosCarrito()) {
                ProductoCarritoResponse productoCarritoResponse = new ProductoCarritoResponse();
                productoCarritoResponse.setId(producto.getId());
                productoCarritoResponse.setCantidad(producto.getCantidad());
                LibroResponse libroResponse = new LibroResponse();
                libroResponse.setAutor(producto.getLibro().getAutor());
                libroResponse.setCantPaginas(producto.getLibro().getCantPaginas());
                libroResponse.setDescripcion(producto.getLibro().getDescripcion());
                libroResponse.setEdicion(producto.getLibro().getEdicion());
                libroResponse.setEditorial(producto.getLibro().getEditorial());
                libroResponse.setGenero(producto.getLibro().getGenero().getNombre());
                libroResponse.setIdioma(producto.getLibro().getIdioma());
                libroResponse.setIsbn(producto.getLibro().getIsbn());
                libroResponse.setPrecio(producto.getLibro().getPrecio());
                libroResponse.setStock(producto.getLibro().getStock());
                libroResponse.setTitulo(producto.getLibro().getTitulo());
                String encodedString;
                    try {
                        encodedString = Base64.getEncoder()
                            .encodeToString(producto.getLibro().getImage().getImage().getBytes(1, (int) producto.getLibro().getImage().getImage().length()));
                            libroResponse.setImage(encodedString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                productoCarritoResponse.setLibro(libroResponse);
                respuesta.add(productoCarritoResponse);
                }
            carritoResponse.setProductoCarrito(respuesta);
            return carritoResponse;
            });
            }
 
     // Busca un carrito por el nombre de usuario
    public Optional<CarritoResponse> getCarritoById(String mail) {
         Optional<Carrito> carritos = carritoRepository.findById(mail);
         return carritos.map(carrito ->{
            CarritoResponse carritoResponse = new CarritoResponse();
            carritoResponse.setMail(carrito.getMail());
            carritoResponse.setTotal(carrito.getTotal());
            List<ProductoCarritoResponse> respuesta = new  ArrayList<>();
            for (ProductoCarrito producto : carrito.getProductosCarrito()) {
                ProductoCarritoResponse productoCarritoResponse = new ProductoCarritoResponse();
                productoCarritoResponse.setId(producto.getId());
                productoCarritoResponse.setCantidad(producto.getCantidad());
                LibroResponse libroResponse = new LibroResponse();
                libroResponse.setAutor(producto.getLibro().getAutor());
                libroResponse.setCantPaginas(producto.getLibro().getCantPaginas());
                libroResponse.setDescripcion(producto.getLibro().getDescripcion());
                libroResponse.setEdicion(producto.getLibro().getEdicion());
                libroResponse.setEditorial(producto.getLibro().getEditorial());
                libroResponse.setGenero(producto.getLibro().getGenero().getNombre());
                libroResponse.setIdioma(producto.getLibro().getIdioma());
                libroResponse.setIsbn(producto.getLibro().getIsbn());
                libroResponse.setPrecio(producto.getLibro().getPrecio());
                libroResponse.setStock(producto.getLibro().getStock());
                libroResponse.setTitulo(producto.getLibro().getTitulo());
                String encodedString;
                    try {
                        encodedString = Base64.getEncoder()
                            .encodeToString(producto.getLibro().getImage().getImage().getBytes(1, (int) producto.getLibro().getImage().getImage().length()));
                            libroResponse.setImage(encodedString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                productoCarritoResponse.setLibro(libroResponse);
                respuesta.add(productoCarritoResponse);
                }
            carritoResponse.setProductoCarrito(respuesta);
            return carritoResponse;
            });
            }
    
    public CarritoResponse createCarrito(String mail) throws ExcepcionCarrito{
        Carrito carritoExistente = carritoRepository.findByMail(mail);
        if ((carritoExistente == null)) {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setMail(mail);
            nuevoCarrito.setTotal(0.0);
            CarritoResponse carritoResponse = new CarritoResponse();
            carritoResponse.setMail(mail);
            carritoResponse.setTotal(0.0);
            carritoRepository.save(nuevoCarrito);
            return carritoResponse;
        }
        throw new ExcepcionCarrito();
    }

    @Override
    public void eliminarCarrito(String mail) {
        Carrito carritoExistente = carritoRepository.findByMail(mail);
    
        if (carritoExistente == null) {
            throw new EntityNotFoundException("No se encontró el carrito con el mail: " + mail);
        }

        vaciarCarrito(mail);

        carritoRepository.delete(carritoExistente);
    }

    @Override
    public void vaciarCarrito(String mail) {
        Carrito carritoExistente = carritoRepository.findByMail(mail);
        
        if (carritoExistente == null) {
            throw new EntityNotFoundException("No se encontró el carrito con el mail:" + mail);
        }

        List<ProductoCarrito> productosDelCarrito = ProdCarritoRepository.findProductosCarritoByMail(mail);
        
        for (ProductoCarrito producto : productosDelCarrito) {
            ProdCarritoRepository.delete(producto);
        }

        carritoExistente.getProductosCarrito().clear(); //con el orphanremoval este metodo tendria que funcionar y no haria falta el delete del prodcarrRepository

        carritoExistente.setTotal(0.0);

        carritoRepository.save(carritoExistente);
        }
        
    }
    
