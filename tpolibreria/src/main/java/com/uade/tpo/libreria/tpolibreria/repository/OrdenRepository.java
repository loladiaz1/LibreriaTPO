package com.uade.tpo.libreria.tpolibreria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.Orden;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long>{

    @Query(value = "select o from Orden o where o.mail = ?1")
    List<Orden> findByMail(String Mail);


    
}
