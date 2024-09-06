package com.uade.tpo.libreria.tpolibreria.repository;

import java.util.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;

@Repository
public interface ProductoCarritoRepository extends JpaRepository<ProductoCarrito, Long>{
    //@Query(value = "select pc from ProductoCarr pc where pc.id = ?1")
    //List<ProductoCarrito> findById(Long Id);
    
    @Query("select p.cantidad from ProductoCarrito p where p.id = :id")
    Optional<Integer> findCantidadById(@Param("id") Long id);

    @Query("select p from ProductoCarrito p where p.carrito.mail = :mail")
    List<ProductoCarrito> findByMail(@Param("mail") String carritoMail);
} 
