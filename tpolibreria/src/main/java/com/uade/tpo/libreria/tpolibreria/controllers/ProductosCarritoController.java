package com.uade.tpo.libreria.tpolibreria.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;
import com.uade.tpo.libreria.tpolibreria.entity.dto.ProductoCarritoRequest;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;
import com.uade.tpo.libreria.tpolibreria.service.ProductoCarritoService;

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
     
    @GetMapping("/{productoCarritoId}")
    //@PathVariable --> indica que el valor del segmento de la URL {productoCarritoId} debe ser vinculado al parámetro productoCarritoId en el método.
    public ResponseEntity<ProductoCarrito> getProductoCarritoById(@PathVariable Long productoCarritoId) {
        //Optional --> se utiliza para representar un valor que puede estar presente o ausente
        Optional<ProductoCarrito> result = ProductoCarritoService.getProductoCarritoById(productoCarritoId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        
            return ResponseEntity.noContent().build(); //es una forma de construir una respuesta HTTP en un controlador de Spring Boot cuando no hay contenido para devolver, pero deseas indicar que la solicitud fue procesada correctamente.
    }

    @PostMapping //("path") --> http://localhost:4002/"path"
    public ResponseEntity<Object> createProductoCarrito(@RequestBody ProductoCarritoRequest ProductoCarritoRequest) 
        throws ExcepcionProductoCarritoDuplicado {
        ProductoCarrito resultado = ProductoCarritoService.createProductoCarrito(
            ProductoCarritoRequest.getId(), 
            ProductoCarritoRequest.getCantidad());
        return ResponseEntity.created(URI.create("/productosCarrito/" + resultado.getId())).body(resultado);
    }
    
}
