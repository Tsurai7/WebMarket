package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.BankCard;
import org.example.code.entities.User;
import org.example.code.services.BankCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cards")
public class BankCardController {

    private final BankCardService bankCardService;

    @PostMapping("/addCard")
    public ResponseEntity<BankCard> addCard(@RequestParam Long userId, @RequestBody BankCard card) {
        return bankCardService.addCard(userId, card) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/removeCard")
    public ResponseEntity<BankCard> removeCard(@RequestParam Long userId, @RequestParam Long cardId) {
        return bankCardService.removeCard(userId, cardId) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateCard")
    public ResponseEntity<BankCard> updateCard(@RequestParam Long cardId, @RequestBody BankCard card) {
        return bankCardService.updateCard(cardId, card) ? new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
