package com.uade.tpo.libreria.tpolibreria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "giftCard_id") //permite nulls
    private GiftCard giftCard;

    @ManyToOne
    @JoinColumn(name = "carrito_nombreUsuario", nullable = false)
    @JsonBackReference
    private Carrito carrito;

    @Transient//dato transitorio, no se guarda en la BD
    public Double TotalDeCompra() {
        Double total = carrito.getPrecio();
        if (giftCard != null) {
            // Aplicar descuento 
            total -= total * (giftCard.getDescuento() / 100);
        }
        // Si no hay GiftCard, total ya está sin el descuento
        return total;
    }

    
}
