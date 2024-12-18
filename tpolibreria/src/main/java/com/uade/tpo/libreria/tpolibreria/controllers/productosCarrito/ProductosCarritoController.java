package com.uade.tpo.libreria.tpolibreria.controllers.productosCarrito;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.uade.tpo.libreria.tpolibreria.controllers.libros.LibroResponse;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionProductoCarritoDuplicado;
import com.uade.tpo.libreria.tpolibreria.service.ProductoCarritoService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController //indica que esa clase va a manejar solicitudes HTTP y devolverá directamente el resultado de los métodos en forma de respuestas HTTP.
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("productosCarrito") //indica que todos los métodos de esa clase que manejen solicitudes HTTP tendrán la URL base "/productosCarrito""
public class ProductosCarritoController {
    @Autowired
    private ProductoCarritoService ProductoCarritoService;

    @GetMapping
    public ResponseEntity<Page<ProductoCarritoResponse>> getProductosCarrito(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
            if (page == null || size == null)
            return ResponseEntity.ok(ProductoCarritoService.getProductosCarrito(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(ProductoCarritoService.getProductosCarrito(PageRequest.of(page, size))); 
    }
     
    @GetMapping("/{productoCarritoId}")
    //@PathVariable --> indica que el valor del segmento de la URL {productoCarritoId} debe ser vinculado al parámetro productoCarritoId en el método.
    public ResponseEntity<ProductoCarritoResponse> getProductoCarritoById(@PathVariable Long productoCarritoId) {
        //Optional --> se utiliza para representar un valor que puede estar presente o ausente
        Optional<ProductoCarritoResponse> result = ProductoCarritoService.getProductoCarritoById(productoCarritoId);
        if (result.isPresent())
            return ResponseEntity.ok(result.get());
        
            return ResponseEntity.noContent().build(); //es una forma de construir una respuesta HTTP en un controlador de Spring Boot cuando no hay contenido para devolver, pero deseas indicar que la solicitud fue procesada correctamente.
    }

    @GetMapping("/{productoCarritoId}/cantidad")
    public ResponseEntity<Integer> getCantidadById(@PathVariable Long productoCarritoId) {
        
        Optional<Integer> cantidad = ProductoCarritoService.getCantidadById(productoCarritoId);
        //El método map se usa para transformar el valor contenido en el Optional
        //Si cantidad contiene un valor, ResponseEntity::ok convierte ese valor en una respuesta HTTP 200 (OK) 
        return cantidad.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
        //^^^esto es como un if simplificado
    }

    @PostMapping //("path") --> http://localhost:4002/"path"
    public ResponseEntity<Object> createProductoCarrito(@RequestBody ProductoCarritoRequest ProductoCarritoRequest) 
        throws ExcepcionProductoCarritoDuplicado {
        ProductoCarritoResponse resultado = ProductoCarritoService.createProductoCarrito(
            ProductoCarritoRequest.getCantidad(),
            ProductoCarritoRequest.getIsbn(),
            ProductoCarritoRequest.getCarrito_mail());
        return ResponseEntity.created(URI.create("/productosCarrito/" + resultado.getId())).body(resultado);
    }
    
    @GetMapping("/{mail}/listaDeProductosCarritoByMail")
    public ResponseEntity<List<ProductoCarritoResponse>> getProductosCarritoByMail(@PathVariable String mail) {
        List<ProductoCarritoResponse> productosCarrito = ProductoCarritoService.getProductosCarritoByMail(mail);
        if (productosCarrito.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(productosCarrito);
        }
    }

    @GetMapping("/{productoCarritoId}/libroById")
    public ResponseEntity<LibroResponse> getLibroById(@PathVariable Long productoCarritoId) {
        LibroResponse libro = ProductoCarritoService.getLibroById(productoCarritoId);
        return ResponseEntity.ok(libro);
  
    }

    
    @GetMapping("/{isbn}/productoCarritoByIsbn")
    public ResponseEntity<List<ProductoCarritoResponse>> getProductoCarritoByIsbn(@PathVariable Long isbn) {
        List<ProductoCarritoResponse> prodsCarr = ProductoCarritoService.getProductosCarritoByIsbn(isbn);

        if (!prodsCarr.isEmpty()) {
            return ResponseEntity.ok(prodsCarr);
        } else {
            return ResponseEntity.notFound().build();
        }
}
      
    @PutMapping("/ActualizarCantLibro") //le tenes que poner en el json el isbn, la cantidad y el mail
    public ResponseEntity<String> actualizarProductoCarrito(@RequestBody ProductoCarritoRequest ProductoCarritoRequest){
        try{
            ProductoCarritoService.actualizarProductoCarritoByIsbn(
                ProductoCarritoRequest.getIsbn(),
                ProductoCarritoRequest.getCantidad(),
                ProductoCarritoRequest.getCarrito_mail());
    
            return ResponseEntity.ok("Producto en el carrito actualizado correctamente.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/EliminarprodCarrito") //le tenes que poner en el json el isbn y el mail
    public ResponseEntity<String> eliminarProductoCarritoByIsbnAndMail(@RequestBody ProductoCarritoRequest ProductoCarritoRequest) {
        try {
            ProductoCarritoService.eliminarProductoCarritoByIsbnAndMail(
                ProductoCarritoRequest.getIsbn(),
                ProductoCarritoRequest.getCarrito_mail());
    
            return ResponseEntity.ok("Producto del carrito eliminado correctamente.");
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: ProductoCarrito no encontrado.");
        }
    }

    @GetMapping("/{productoCarritoId}/MailById")
    public ResponseEntity<String> getMailById(@PathVariable Long productoCarritoId) {
        String mail = ProductoCarritoService.getMailById(productoCarritoId);
        if (mail == null || mail.isEmpty()) {
            return ResponseEntity.notFound().build();  
        }
        return ResponseEntity.ok(mail);
    }
    
    @DeleteMapping("/EliminarprodCarritoByIsbn")
    public ResponseEntity<String> eliminarProductoCarritoByIsbn(@RequestBody ProductoCarritoRequest ProductoCarritoRequest) {
        try {
            ProductoCarritoService.eliminarProductoCarritoByIsbn(ProductoCarritoRequest.getIsbn());
    
            return ResponseEntity.ok("Producto del carrito eliminado correctamente.");
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: ProductoCarrito no encontrado.");
        }
    }
}
