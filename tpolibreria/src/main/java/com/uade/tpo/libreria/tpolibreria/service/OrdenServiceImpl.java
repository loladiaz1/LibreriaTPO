package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.repository.OrdenRepository;
import com.uade.tpo.libreria.tpolibreria.repository.UsuarioRepository;
import com.uade.tpo.libreria.tpolibreria.controllers.orden.OrdenRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Orden;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;
import com.uade.tpo.libreria.tpolibreria.repository.GiftCardRepository;


@Service
public class OrdenServiceImpl implements OrdenService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private OrdenRepository OrdenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GiftCardRepository giftCardRepository;

    @Override
    public Orden createOrden(OrdenRequest ordenRequest) {
        Carrito carrito = carritoRepository.findByMail(ordenRequest.getMail());
            //.orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + mail));

        Orden ordenNueva = new Orden();
        ordenNueva.setGiftCard(giftCardRepository.findByCodigo(ordenRequest.getCodigo()));
        ordenNueva.setTotalSinDescuento(carrito.getTotal());
        if (ordenNueva.getGiftCard() != null){
            ordenNueva.setTotalConDescuento(carrito.getTotal()* ordenNueva.getGiftCard().getDescuento());
            ordenNueva.setDescuento(ordenNueva.getGiftCard().getDescuento());  
        }else{
            ordenNueva.setTotalConDescuento(carrito.getTotal());
            ordenNueva.setDescuento(0.0);
        }
        Usuario usuario = usuarioRepository.findByMail(ordenRequest.getMail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con el mail: " + ordenRequest.getMail()));
        ordenNueva.setUsuario(usuario);
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

