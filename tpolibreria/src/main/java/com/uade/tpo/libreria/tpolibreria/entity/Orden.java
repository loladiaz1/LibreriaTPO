 
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
import lombok.Data;

@Entity
@Data
public class Orden {

    public Orden(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //se genera solo el ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mail_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "carrito_mailUsuario")
    @JsonBackReference
    private Carrito carrito;

    @OneToOne
    @JoinColumn(name = "giftCard_id")
    private GiftCard giftCard;

    @Column
    private Double totalSinDescuento;

    @Column
    private Double totalConDescuento;

    @Column
    private Double Descuento;


    
}

