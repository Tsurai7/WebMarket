package com.main.webmarket.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "bank_cards")
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private User holder;

    private String owner = "";

    private String  number = "";

    private String expirationDate = "";

    private int cvc;

    public BankCard(String owner, String number, String expirationDate, int cvc) {
        this.owner = owner;
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
    }

    @PreRemove
    public  void removeHolder()
    {
        this.holder = null;
    }

    public BankCard updateProperties(BankCard card) {
        this.setOwner(card.getOwner());
        this.setNumber(card.getNumber());
        this.setExpirationDate(card.getExpirationDate());
        this.setCvc(card.getCvc());
        return this;
    }
}
