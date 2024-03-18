package org.example.code.services;

import org.example.code.entities.BankCard;
import org.example.code.entities.User;
import org.example.code.repositories.BankCardRepository;
import org.example.code.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BankCardService {
    private static final Logger logger = LoggerFactory.getLogger(BankCardService.class);

    private final UserRepository userRepository;

    private final BankCardRepository bankCardRepository;

    @Autowired
    public BankCardService(UserRepository userRepository, BankCardRepository bankCardRepository) {
        this.userRepository = userRepository;
        this. bankCardRepository = bankCardRepository;
    }

    public void addCard(Long userId, BankCard card) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with id: {}", userId);
            throw  new NoSuchElementException("User not found with id: " + userId);
        });

        bankCardRepository.save(card);
        user.addCard(card);

        userRepository.save(user);
    }

    public void removeCard(Long userId, Long cardId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("Card not found with id: {}", userId);
            throw  new NoSuchElementException("Card not found with id: " + userId);
        });

        BankCard card = bankCardRepository.findById(cardId).orElseThrow(() -> {
            logger.error("Card not found with id: {}", cardId);
            throw  new NoSuchElementException("Card not found with id: " + cardId);
        });

        user.removeCard(card);
        bankCardRepository.delete(card);
    }

    public BankCard updateCard(Long id, BankCard card) {
        BankCard cardInDb = bankCardRepository.findById(id).orElseThrow(() -> {
            logger.error("Card not found with id: {}", id);
            throw  new NoSuchElementException("Card not found with id: " + id);
        });

        cardInDb.setOwner(card.getOwner());
        cardInDb.setNumber(card.getNumber());
        cardInDb.setExpirationDate(card.getExpirationDate());
        cardInDb.setCvc(card.getCvc());

        bankCardRepository.save(cardInDb);

        return cardInDb;
    }
}
