package com.main.webmarket.services;


import com.main.webmarket.entities.BankCard;
import com.main.webmarket.entities.User;
import com.main.webmarket.repositories.BankCardRepository;
import com.main.webmarket.repositories.UserRepository;
import jakarta.transaction.Transactional;
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

    private static final String USER_NOT_FOUND_MESSAGE = "User not found with id: %d";

    private static final String CARD_NOT_FOUND_MESSAGE = "Card not found with id: %d";

    @Autowired
    public BankCardService(UserRepository userRepository, BankCardRepository bankCardRepository) {
        this.userRepository = userRepository;
        this. bankCardRepository = bankCardRepository;
    }


    @Transactional
    public void addCard(Long userId, BankCard card) {
        User user = userRepository.findById(userId)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        user.addCard(card);
        userRepository.save(user);
    }


    @Transactional
    public void removeCard(Long userId, Long cardId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        BankCard card = user.getBankCards().stream()
            .filter(c -> c.getId().equals(cardId))
            .findFirst()
            .orElseThrow(() ->
                new NoSuchElementException(String.format(CARD_NOT_FOUND_MESSAGE, cardId)));

        user.removeCard(card);
        bankCardRepository.delete(card);
    }


    @Transactional
    public BankCard updateCard(Long id, BankCard card) {
        return bankCardRepository.findById(id)
            .orElseThrow(() ->
                    new NoSuchElementException(String.format(CARD_NOT_FOUND_MESSAGE, id)))
            .updateProperties(card);
    }
}
