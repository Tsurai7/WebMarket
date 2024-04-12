package com.main.webmarket.services;

import com.main.webmarket.entities.BankCard;
import com.main.webmarket.repositories.BankCardRepository;
import com.main.webmarket.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankCardServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BankCardRepository bankCardRepository;

    @InjectMocks
    private BankCardService bankCardService;

    @Test
    void testAddCard_UserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        BankCard card = new BankCard();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> bankCardService.addCard(userId, card));

        verify(userRepository, times(1)).findById(userId);
        verify(bankCardRepository, never()).save(any());
    }

    @Test
    void testRemoveCard_UserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        Long cardId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> bankCardService.removeCard(userId, cardId));

        verify(userRepository, times(1)).findById(userId);
        verify(bankCardRepository, never()).findById(any());
        verify(bankCardRepository, never()).delete(any());
    }

    @Test
    void testUpdateCard_CardExists_ShouldUpdateCard() {
        Long cardId = 1L;
        BankCard existingCard = new BankCard();

        existingCard.setId(cardId);
        existingCard.setOwner("Old Owner");
        existingCard.setNumber("1234");
        existingCard.setExpirationDate(LocalDate.of(2022, 12, 31).toString());
        existingCard.setCvc(123);

        BankCard updatedCard = new BankCard();
        updatedCard.setOwner("New Owner");
        updatedCard.setNumber("5678");
        updatedCard.setExpirationDate(LocalDate.of(2023, 6, 30).toString());
        updatedCard.setCvc(456);

        when(bankCardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(bankCardRepository.save(existingCard)).thenReturn(existingCard);

        // Act
        BankCard result = bankCardService.updateCard(cardId, updatedCard);

        // Assert
        assertEquals(updatedCard.getOwner(), result.getOwner());
        assertEquals(updatedCard.getNumber(), result.getNumber());
        assertEquals(updatedCard.getExpirationDate(), result.getExpirationDate());
        assertEquals(updatedCard.getCvc(), result.getCvc());

        verify(bankCardRepository, times(1)).findById(cardId);
        verify(bankCardRepository, times(1)).save(existingCard);
    }

    @Test
    void testUpdateCard_CardDoesNotExist_ShouldThrowException() {
        Long cardId = 1L;
        BankCard updatedCard = new BankCard();

        when(bankCardRepository.findById(cardId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NoSuchElementException.class, () -> bankCardService.updateCard(cardId, updatedCard));

        verify(bankCardRepository, times(1)).findById(cardId);
        verify(bankCardRepository, never()).save(any());
    }
}
