package com.main.webmarket.services;

import com.main.webmarket.entities.BankCard;
import com.main.webmarket.repositories.BankCardRepository;
import com.main.webmarket.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.NoSuchElementException;
import java.util.Optional;


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

        assertThrows(NoSuchElementException.class, () -> bankCardService.addCard(userId, card));

        verify(userRepository, times(1)).findById(userId);
        verify(bankCardRepository, never()).save(any());
    }

    @Test
    void testRemoveCard_UserDoesNotExist_ShouldThrowException() {
        Long userId = 1L;
        Long cardId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bankCardService.removeCard(userId, cardId));

        verify(userRepository, times(1)).findById(userId);
        verify(bankCardRepository, never()).findById(any());
        verify(bankCardRepository, never()).delete(any());
    }

    @Test
    void testUpdateCard_CardDoesNotExist_ShouldThrowException() {
        long nonExistentCardId = 999L;

        when(bankCardRepository.findById(nonExistentCardId)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> bankCardService.updateCard(nonExistentCardId, new BankCard())
        );
        verify(bankCardRepository, times(1)).findById(nonExistentCardId);
        verify(bankCardRepository, never()).save(any(BankCard.class));
    }
}
