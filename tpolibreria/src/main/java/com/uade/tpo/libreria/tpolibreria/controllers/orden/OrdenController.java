package com.uade.tpo.libreria.tpolibreria.controllers.orden;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.Orden;
import com.uade.tpo.libreria.tpolibreria.service.OrdenService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // Crear una orden a partir del email del usuario
    @PostMapping
    public ResponseEntity<Orden> crearOrden(@RequestBody OrdenRequest ordenRequest) {
        Orden nuevaOrden = ordenService.createOrden(ordenRequest);
        return ResponseEntity.created(URI.create("/orden/" + nuevaOrden.getId())).body(nuevaOrden);
    }

    // Obtener una lista paginada de todas las órdenes
    @GetMapping
    public ResponseEntity<Page<Orden>> obtenerOrdenes(Pageable pageable) {
        Page<Orden> ordenes = ordenService.getOrdenes(pageable);
        return ResponseEntity.ok(ordenes);
    }

    // Obtener las órdenes asociadas a un correo específico
    @GetMapping("/usuario/{mail}")
    public ResponseEntity<List<Orden>> obtenerOrdenesPorMail(@PathVariable String mail) {
        List<Orden> ordenes = ordenService.getOrdenesByMail(mail);
        if (ordenes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(ordenes);
    }

    // Eliminar una orden por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> getOrdenById(@PathVariable Long id) {
        Optional<Orden> orden = ordenService.getOrdenById(id);
        if (orden.isPresent()) {
            return ResponseEntity.ok(orden.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> updateOrden(@PathVariable Long id, @RequestBody OrdenRequest ordenRequest) {
        try {
            ordenService.updateOrden(id, ordenRequest);
            return ResponseEntity.ok("Orden actualizada.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

