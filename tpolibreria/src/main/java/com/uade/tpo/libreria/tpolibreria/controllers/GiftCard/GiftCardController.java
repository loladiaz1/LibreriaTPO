package com.uade.tpo.libreria.tpolibreria.controllers.GiftCard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;
import com.uade.tpo.libreria.tpolibreria.repository.GiftCardRepository;

@RestController
@RequestMapping("/giftcards")
public class GiftCardController {

    @Autowired
    private GiftCardRepository giftCardRepository;

    //obtener un GiftCard por id

    @GetMapping("/{id}")
    public ResponseEntity<GiftCard> getGiftCardById(@PathVariable("id") Long id) {
        Optional<GiftCard> giftCard = giftCardRepository.findById(id);
        return giftCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //crear un nuevo GiftCard


    @PostMapping
    public ResponseEntity<GiftCard> createGiftCard(@RequestBody GiftCard giftCard) {
        if (giftCard.getDescuento() == null) {
            giftCard.setDescuento(0.0);  // Establece descuento a 0 si es null
        }
        GiftCard savedGiftCard = giftCardRepository.save(giftCard);
        return new ResponseEntity<>(savedGiftCard, HttpStatus.CREATED);
    }
    
}
