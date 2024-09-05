package com.uade.tpo.libreria.tpolibreria.controllers.carritos;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionCarrito;
import com.uade.tpo.libreria.tpolibreria.service.CarritoService;

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

    @GetMapping("/{carritoId}")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable String carritoId) {
        Optional<Carrito> result = carritoService.getCarritoById(carritoId);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
     public ResponseEntity<Object> createCarrito(@RequestBody CarritoRequest carritoRequest)
            throws ExcepcionCarrito{
                Carrito result = carritoService.createCarrito(carritoRequest.getMail() ,carritoRequest.getPrecio());
                return ResponseEntity.created(URI.create("/carritos/" + result.getMail() + result.getPrecio())).body(result);
            }

    


    
}
