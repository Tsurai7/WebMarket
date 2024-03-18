package org.example.code.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "bank_card")
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
}
