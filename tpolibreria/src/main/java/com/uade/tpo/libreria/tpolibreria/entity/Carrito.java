package com.uade.tpo.libreria.tpolibreria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
<<<<<<< HEAD
//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;

=======
import com.fasterxml.jackson.annotation.JsonManagedReference;
>>>>>>> main
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Carrito {

    public Carrito(){
    }

    @Id
    @Column
    private String mail;

    @Column
    private double precio;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductoCarrito> productosCarrito;

    @OneToOne(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Usuario usuario;
}




