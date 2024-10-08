package com.uade.tpo.libreria.tpolibreria.repository;

import java.util.List;
import java.util.Optional;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.libreria.tpolibreria.entity.Libro;
import com.uade.tpo.libreria.tpolibreria.entity.ProductoCarrito;

@Repository
public interface ProductoCarritoRepository extends JpaRepository<ProductoCarrito, Long>{
    @Query("select p.libro from ProductoCarrito p where p.id = :id")
    Optional<Libro> findLibroByProductoCarritoId(@Param("id") Long id);

    @Query("select p.cantidad from ProductoCarrito p where p.id = :id")
    Optional<Integer> findCantidadById(@Param("id") Long id);

    @Query("select p from ProductoCarrito p where p.carrito.mail = :mail")
    List<ProductoCarrito> findByMail(@Param("mail") String carritoMail);

    //libro.isbn --> la consulta debe acceder a isbn a través de la relación con Libro
    @Query("select p from ProductoCarrito p where p.libro.isbn = :isbn")
    List<ProductoCarrito> findByIsbn(@Param("isbn") Long isbn);
    
    @Query("select p.carrito.mail from ProductoCarrito p where p.id = :id")
    String findMailById(@Param("id") Long id); 

    @Query("select p from ProductoCarrito p where p.carrito.mail = :mail")
    List<ProductoCarrito> findProductosCarritoByMail(@Param("mail") String mail); 

    @Query("select p from ProductoCarrito p where p.libro.isbn = :isbn and p.carrito.mail = :mail")
    Optional<ProductoCarrito> findByIsbnAndCarritoMail(@Param("isbn") Long isbn, @Param("mail") String mail);
} 
