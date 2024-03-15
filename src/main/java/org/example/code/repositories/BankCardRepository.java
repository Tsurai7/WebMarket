package org.example.code.repositories;

import org.example.code.entities.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {
    BankCard findByCardNumber(String number);
}
