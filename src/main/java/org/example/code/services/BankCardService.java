package org.example.code.services;

import lombok.AllArgsConstructor;
import org.example.code.entities.BankCard;
import org.example.code.entities.User;
import org.example.code.repositories.BankCardRepository;
import org.example.code.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankCardService {

    private final UserRepository userRepository;

    private final BankCardRepository bankCardRepository;

    public boolean addCard(Long userId, BankCard card) {

        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {

            BankCard existingCard = bankCardRepository.findByNumber(card.getNumber());

            if (existingCard != null) {
                return false;
            }

            bankCardRepository.save(card);
            user.addCard(card);

            userRepository.save(user);

            return true;
        }

        return false;
    }

    public boolean removeCard(Long userId, Long cardId) {

        User user = userRepository.findById(userId).orElse(null);
        BankCard card = bankCardRepository.findById(cardId).orElse(null);

        if (user != null  && card != null) {
            user.removeCard(card);
            bankCardRepository.delete(card);

            return true;
        }

        return false;
    }

    public boolean updateCard(Long cardId, BankCard card) {
        BankCard cardInDb = bankCardRepository.findById(cardId).orElse(null);

        if (cardInDb != null) {
            cardInDb.setOwner(card.getOwner());
            cardInDb.setNumber(card.getNumber());
            cardInDb.setExpirationDate(card.getExpirationDate());
            cardInDb.setCvc(card.getCvc());

            bankCardRepository.save(cardInDb);

            return true;
        }

        return false;
    }
}
