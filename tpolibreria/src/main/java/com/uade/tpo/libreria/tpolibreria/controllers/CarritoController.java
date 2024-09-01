package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.service.CarritoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("carritos") //creo que carrito es uno solo pero es para diferenciar
public class CarritoController {

     @Autowired
    private CarritoService carritoService;

    @GetMapping
    public  ResponseEntity<Page<Carrito>> getCarritos(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(carritoService.getCarritos(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(carritoService.getCarritos(PageRequest.of(page, size)));

    }
    
}
