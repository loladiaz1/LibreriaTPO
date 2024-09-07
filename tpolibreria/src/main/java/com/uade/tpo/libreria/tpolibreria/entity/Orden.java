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
import lombok.Data;

@Entity
@Data
@Table(name = "ordenes")
public class Orden {

    public Orden(){
    }

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

    @Column
    private Double TotalSinDescuento(){
        Double Total = 0.0;
        if (carrito != null){
            Total = carrito.getPrecio();
            
        }
        return Total;

    }

    @Column
    private Double TotalConDescueno(){
        Double total = 0.0;
        if (carrito != null){
            if (giftCard != null) {
                // Aplicar descuento 
                total -= total * (giftCard.getDescuento() / 100);
            }
            // Si no hay GiftCard, total ya está sin el descuento
            return total;
        }
        return total;
        

    }

    
}
