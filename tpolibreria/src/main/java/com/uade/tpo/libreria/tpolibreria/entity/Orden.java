 
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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //se genera solo el ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mail_id", nullable = false)
    private String mailUsuario;

    @ManyToOne
    @JoinColumn(name = "carrito_mailUsuario", nullable = false)
    @JsonBackReference
    private Carrito carrito;

    @OneToOne
    @JoinColumn(name = "giftCard_id", nullable = false)
    private GiftCard giftCard;

    @Column
    private Double totalSinDescuento;

    @Column
    private Double totalConDescuento;

    @Column
    private Double Descuento;


    
}

