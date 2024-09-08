package com.uade.tpo.libreria.tpolibreria.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.uade.tpo.libreria.tpolibreria.entity.Orden;

public interface OrdenService {

    public Orden createOrden(String mail);

    Page<Orden> getOrdenes(Pageable pageable);

    List<Orden>  getOrdenesByMail(String mail);

    public void eliminarOrden(Long id);

   
}
