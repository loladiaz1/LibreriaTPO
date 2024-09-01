package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.entity.dto.ProductoCarritoRequest;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;
import com.uade.tpo.libreria.tpolibreria.service.ProductoCarritoService;

import java.net.URI;
//import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;


@RestController //indica que esa clase va a manejar solicitudes HTTP y devolverá directamente el resultado de los métodos en forma de respuestas HTTP.
@RequestMapping("productosCarrito") //indica que todos los métodos de esa clase que manejen solicitudes HTTP tendrán la URL base "/productosCarrito""
public class ProductosCarritoController {
    @Autowired
    private ProductoCarritoService ProductoCarritoService;

    @GetMapping
    public ResponseEntity<Page<ProductoCarrito>> getProductosCarrito(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
            if (page == null || size == null)
            return ResponseEntity.ok(ProductoCarritoService.getProductosCarrito(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(ProductoCarritoService.getProductosCarrito(PageRequest.of(page, size))); 
    }
     
    /*
     * @PostMapping
    public ResponseEntity<Object> createGenero(@RequestBody GeneroRequest GeneroRequest)
            throws ExcepcionGeneroDuplicado {
        Genero result = GeneroService.createGenero(GeneroRequest.getNombre());
        return ResponseEntity.created(URI.create("/generos/" + result.getId())).body(result);
    }
     */

    //Hola

    @PostMapping //("path") --> http://localhost:4002/"path"
    public ResponseEntity<Object> createProductoCarrito(@RequestBody ProductoCarritoRequest ProductoCarritoRequest) 
        throws ExcepcionProductoCarritoDuplicado {
        ProductoCarrito resultado = ProductoCarritoService.createProductoCarrito(
            ProductoCarritoRequest.getId(), 
            ProductoCarritoRequest.getCantidad());
        return ResponseEntity.created(URI.create("/productosCarrito/" + resultado.getId())).body(resultado);
    }
    
}
