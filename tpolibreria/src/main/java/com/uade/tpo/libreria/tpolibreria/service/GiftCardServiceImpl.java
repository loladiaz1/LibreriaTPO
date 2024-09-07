package com.uade.tpo.libreria.tpolibreria.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionGiftCard;
import com.uade.tpo.libreria.tpolibreria.repository.GiftCardRepository;

@Service
public class GiftCardServiceImpl implements GiftCardService {

    @Autowired
    private GiftCardRepository giftCardRepository;

    @Override
    public GiftCard updateGiftCard(Long id, GiftCard giftCardDetails) throws ExcepcionGiftCard {
        GiftCard giftCard = giftCardRepository.findById(id)
                .orElseThrow(() -> new ExcepcionGiftCard("GiftCard no encontrada con id: " + id));

        giftCard.setDescuento(giftCardDetails.getDescuento());
        giftCard.setCodigo(giftCardDetails.getCodigo());

        return giftCardRepository.save(giftCard);
    }

    @Override
    public void deleteGiftCard(Long id) throws ExcepcionGiftCard {
        GiftCard giftCard = giftCardRepository.findById(id)
                .orElseThrow(() -> new ExcepcionGiftCard("GiftCard no encontrada con id: " + id));

        giftCardRepository.delete(giftCard);
    }

    public List<GiftCard> getAllGiftCards() {
        return giftCardRepository.findAll(); 
    }

    
}


