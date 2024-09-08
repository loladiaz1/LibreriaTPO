package com.uade.tpo.libreria.tpolibreria.controllers.orden;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.Orden;
import com.uade.tpo.libreria.tpolibreria.service.OrdenService;

@RestController
@RequestMapping("/ordenes")
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    // Crear una orden a partir del email del usuario
    @PostMapping("/crear")
    public ResponseEntity<Orden> crearOrden(@RequestParam String mail) {
        try {
            Orden nuevaOrden = ordenService.createOrden(mail);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
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
}

