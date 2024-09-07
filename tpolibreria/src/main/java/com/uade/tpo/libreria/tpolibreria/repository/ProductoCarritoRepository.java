package com.uade.tpo.libreria.tpolibreria.repository;

import java.util.List;
import java.util.Optional;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.libreria.tpolibreria.entity.Carrito;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;

@Repository
public interface ProductoCarritoRepository extends JpaRepository<ProductoCarrito, Long>{
    //@Query(value = "select pc from ProductoCarr pc where pc.id = ?1")
    //List<ProductoCarrito> findById(Long Id);
    
    @Query("SELECT p.cantidad FROM ProductoCarrito p WHERE p.id = :id")
    Optional<Integer> findCantidadById(@Param("id") Long id);

    //@Query(value = "select p from ProductoCarrito p where p.carrito_mail = ?1")
    //List<Carrito> findByMail(String mail);
} 
