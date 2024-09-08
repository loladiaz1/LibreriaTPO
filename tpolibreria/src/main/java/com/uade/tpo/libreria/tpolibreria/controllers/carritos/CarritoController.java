package com.uade.tpo.libreria.tpolibreria.controllers.carritos;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import org.springframework.web.bind.annotation.PutMapping;


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
                Carrito result = carritoService.createCarrito(carritoRequest.getMail());
                return ResponseEntity.created(URI.create("/carritos/" + result.getMail())).body(result);
            }

    @DeleteMapping("{mail}/EliminarCarritoPorMail")
    public ResponseEntity<String> eliminarCarrito(@PathVariable String mail){
        try {
            carritoService.eliminarCarrito(mail);
            return ResponseEntity.ok("El carrito ha sido eliminado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Carrito no encontrado.");
        }
    }

    @PutMapping("{mail}/VaciarCarrito")
    public ResponseEntity<String> vaciarCarrito(@PathVariable String mail) {
        carritoService.vaciarCarrito(mail);
        return ResponseEntity.ok("El carrito se ha vaciado");
    }

}
