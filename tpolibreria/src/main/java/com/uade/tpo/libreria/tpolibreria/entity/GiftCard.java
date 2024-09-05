package com.uade.tpo.libreria.tpolibreria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "gift_cards")
public class GiftCard {

    public GiftCard(){
        
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //no puede ser null la tabla, se tendria que poner 0 en descuento 
    //en orden si va a poder ser null
    @Column(nullable = false)
    private Double descuento;

     //no puede ser null la tabla y la gift es unica
    @Column(nullable = false, unique = true)
    private String codigo;


    
}
