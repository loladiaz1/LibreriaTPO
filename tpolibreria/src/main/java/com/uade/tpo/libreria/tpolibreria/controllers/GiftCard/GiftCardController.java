package com.uade.tpo.libreria.tpolibreria.controllers.GiftCard;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.GiftCard;
import com.uade.tpo.libreria.tpolibreria.exceptions.ExcepcionGiftCard;
import com.uade.tpo.libreria.tpolibreria.repository.GiftCardRepository;
import com.uade.tpo.libreria.tpolibreria.service.GiftCardService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/giftcards")
public class GiftCardController {

    @Autowired
    private GiftCardRepository giftCardRepository;

    @Autowired
    private GiftCardService giftCardService;


    //obtener un GiftCard por id
    @GetMapping("/{id}")
    public ResponseEntity<GiftCard> getGiftCardById(@PathVariable("id") Long id) {
        Optional<GiftCard> giftCard = giftCardRepository.findById(id);
        return giftCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GiftCard>> getAllGiftCards() {
        List<GiftCard> giftCards = giftCardService.getAllGiftCards();
        return ResponseEntity.ok(giftCards);
    }

    //crear un nuevo gift
    @PostMapping
    public ResponseEntity<GiftCard> createGiftCard(@RequestBody GiftCardRequest giftCardRequest) {
        GiftCard giftCard = new GiftCard();
        giftCard.setDescuento(giftCardRequest.getDescuento() != null ? giftCardRequest.getDescuento() : 0.0);
        giftCard.setCodigo(giftCardRequest.getCodigo());
        // GuardarGiftCard
        GiftCard savedGiftCard = giftCardRepository.save(giftCard);

        return new ResponseEntity<>(savedGiftCard, HttpStatus.CREATED);
    }

    //actualiza gift
    @PutMapping("/{id}")
    public ResponseEntity<GiftCard> updateGiftCard(
            @PathVariable Long id, 
            @RequestBody GiftCardRequest giftCardRequest) throws ExcepcionGiftCard {
        GiftCard giftCardDetails = new GiftCard();
        giftCardDetails.setDescuento(giftCardRequest.getDescuento() != null ? giftCardRequest.getDescuento() : 0.0);
        giftCardDetails.setCodigo(giftCardRequest.getCodigo());
        // Actualizar la GiftCard usando el servicio
        GiftCard updatedGiftCard = giftCardService.updateGiftCard(id, giftCardDetails);
        return ResponseEntity.ok(updatedGiftCard);
    }

    //borra la gift
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCard(@PathVariable Long id) throws ExcepcionGiftCard {
        giftCardService.deleteGiftCard(id);
        return ResponseEntity.noContent().build();
    }
    
}
