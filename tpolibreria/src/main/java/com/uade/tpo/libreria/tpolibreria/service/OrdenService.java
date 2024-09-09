package com.uade.tpo.libreria.tpolibreria.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uade.tpo.libreria.tpolibreria.controllers.orden.OrdenRequest;
import com.uade.tpo.libreria.tpolibreria.entity.Orden;

public interface OrdenService {

    public Orden createOrden(OrdenRequest ordenRequest);

    Page<Orden> getOrdenes(Pageable pageable);

    List<Orden>  getOrdenesByMail(String mail);

    public void eliminarOrden(Long id);

   
}
