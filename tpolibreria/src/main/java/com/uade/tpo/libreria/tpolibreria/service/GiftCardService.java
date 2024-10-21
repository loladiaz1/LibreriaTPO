package com.uade.tpo.libreria.tpolibreria.service;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionGiftCard;

public interface GiftCardService {
    GiftCard updateGiftCard (Long id, GiftCard giftCardDetails) throws ExcepcionGiftCard;
    
    void deleteGiftCard(Long id) throws ExcepcionGiftCard;
    
    List<GiftCard> getAllGiftCards();

    Optional<GiftCard> getByCodigo (String codigo);
}
