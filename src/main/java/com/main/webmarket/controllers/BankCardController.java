package com.main.webmarket.controllers;


import com.main.webmarket.entities.BankCard;
import com.main.webmarket.services.BankCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class BankCardController {
    private static final Logger logger = LoggerFactory.getLogger(BankCardController.class);

    private final BankCardService bankCardService;

    @Autowired
    public BankCardController(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @PostMapping("/create")
    public ResponseEntity<BankCard> addCard(@RequestParam Long userId, @RequestBody BankCard card) {
        logger.info("Endpoint called: /api/cards/create");
        bankCardService.addCard(userId, card);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<BankCard> removeCard(@RequestParam Long userId, @RequestParam Long cardId) {
        logger.info("Endpoint called: /api/cards/remove");
        bankCardService.removeCard(userId, cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<BankCard> updateCard(@RequestParam Long cardId, @RequestBody BankCard card) {
        logger.info("Endpoint called: /api/cards/update");
        return new ResponseEntity<>(bankCardService.updateCard(cardId, card), HttpStatus.NO_CONTENT);
    }
}
