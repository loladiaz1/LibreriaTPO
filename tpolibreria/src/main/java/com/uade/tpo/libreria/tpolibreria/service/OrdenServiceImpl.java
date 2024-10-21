package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.repository.OrdenRepository;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;
import com.uade.tpo.libreria.tpolibreria.controllers.orden.OrdenRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.Orden;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.GiftCardRepository;
import com.uade.tpo.libreria.tpolibreria.repository.LibroRepository;

@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private OrdenRepository OrdenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GiftCardRepository giftCardRepository;

    @Autowired
    private LibroRepository LibroRepository;

    @Override
    public Orden createOrden(OrdenRequest ordenRequest) {
        Carrito carrito = carritoRepository.findByMail(ordenRequest.getMail());
            //.orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + mail));

        Orden ordenNueva = new Orden();
        Optional<GiftCard> giftCard =giftCardRepository.findByCodigo(ordenRequest.getCodigo());
        ordenNueva.setGiftCard(giftCard.get());
        ordenNueva.setTotalSinDescuento(carrito.getTotal());
        if (ordenNueva.getGiftCard() != null){
            ordenNueva.setTotalConDescuento(carrito.getTotal() * (1 - ordenNueva.getGiftCard().getDescuento()));
            ordenNueva.setDescuento(ordenNueva.getGiftCard().getDescuento());  
        }else{
            ordenNueva.setTotalConDescuento(carrito.getTotal());
            ordenNueva.setDescuento(0.0);
        }
        Usuario usuario = usuarioRepository.findByMail(ordenRequest.getMail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el mail: " + ordenRequest.getMail()));
        ordenNueva.setUsuario(usuario);

        //Obtener la cantidad de libros que se compro por cada libro y restarle esa cantidad a cada libro en su repository:
        List<ProductoCarrito> productosCarrito= carrito.getProductosCarrito();
        for (ProductoCarrito prodcarr : productosCarrito) {
            int cantARestar = prodcarr.getCantidad();
            Libro libro = prodcarr.getLibro();
            int nuevaCantidad = libro.getStock() - cantARestar;
            
            if (nuevaCantidad < 0) {
                throw new IllegalArgumentException("Error al comprar.");
            }
            libro.setStock(nuevaCantidad);
            LibroRepository.save(libro);

            /*
             * if (nuevaCantidad == 0){
             * IMPLEMENTACION DE QUE EL LIBRO SE VUELVA GRIS
             * }
             */
        }
        //Vacio de carrito, en vaciar carrito se guarda el carrito nuevo en repository:
        carritoService.vaciarCarrito(ordenRequest.getMail());

        return OrdenRepository.save(ordenNueva);

    }

    @Override
    public Page<Orden> getOrdenes(Pageable pageable) {
        return OrdenRepository.findAll(pageable);
    }

    @Override
    public List<Orden> getOrdenesByMail(String mail) {
        return OrdenRepository.findByMail(mail);
    
    }

    @Override
    public void eliminarOrden(Long id) {
        Orden Orden = OrdenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con el id: " + id));

        OrdenRepository.delete(Orden);
        }
        
        
    }

