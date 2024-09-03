package com.uade.tpo.libreria.tpolibreria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    
    @Column
    private Long count;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "giftCard_id") //permite nulls
    private GiftCard giftCard;

    @Transient //dato transitorio, no se guarda en la BD
    private Double precioUnitario;

    @Transient
    public Double TotalDeCompra() {

        Double total = precioUnitario * count;
        if (giftCard != null) {
            // Aplicar descuento 
            total -= total * (giftCard.getDescuento() / 100);
        }
        // Si no hay GiftCard, total ya está sin descuento
        return total;
    }

    
}
