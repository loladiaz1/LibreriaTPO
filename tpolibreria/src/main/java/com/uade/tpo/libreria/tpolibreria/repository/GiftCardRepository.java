package com.uade.tpo.libreria.tpolibreria.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;

@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {

    @Query(value = "select gc from GiftCard gc where gc.codigo = ?1")
    GiftCard findByCodigo(String codigo);


    
}
