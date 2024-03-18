package org.example.code.repositories;

import org.example.code.entities.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {
    BankCard findByNumber(String number);
}