package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uade.tpo.libreria.tpolibreria.repository.OrdenRepository;


import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Orden;
import com.uade.tpo.libreria.tpolibreria.repository.CarritoRepository;


@Service
public class OrdenServiceImpl implements OrdenService {

     @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private OrdenRepository OrdenRepository;

    @Override
    public Orden createOrden(String mail) {

        Carrito carrito = carritoRepository.findById(mail)
            .orElseThrow(() -> new RuntimeException("No se encontró un carrito asociado al correo: " + mail));

        Orden ordenNueva = new Orden();
        //ordenNueva.setMailUsuario(mail);
        ordenNueva.setTotalSinDescuento(carrito.getTotal());
        ordenNueva.setTotalConDescuento(carrito.getTotal()*ordenNueva.getGiftCard().getDescuento());
        ordenNueva.setDescuento(ordenNueva.getGiftCard().getDescuento());

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

