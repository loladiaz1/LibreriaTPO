package com.uade.tpo.libreria.tpolibreria.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.libreria.tpolibreria.entity.Carrito;

@Repository
public interface CarritoRepository  extends JpaRepository<Carrito, String>{

    @Query(value = "select c from Carrito c where c.mail = ?1")
    Carrito findByMail(String mail);

    
}
