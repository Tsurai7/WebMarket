package com.main.webmarket.repositories;

import com.main.webmarket.entities.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("checkstyle:MissingJavadocType")
public interface BankCardRepository extends JpaRepository<BankCard, Long> {
    BankCard findByNumber(String number);
}
