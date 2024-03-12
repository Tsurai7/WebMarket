package org.example.code.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<BankCard> bankCards = new HashSet<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.getUsers().add(this);
    }

    public void addCard(BankCard card) {
        bankCards.add(card);
        card.setHolder(this);
    }

    public void removeCard(BankCard card) {
        bankCards.remove(card);
        card.setHolder(null);
    }

    public void removeAllCards() {
        bankCards.forEach(card -> card.setHolder(null));
    }

    public void removeProduct(Product product) {
        products.removeAll(Collections.singleton(product));
        product.getUsers().remove(this);
    }

    public void removeAllProducts() {
        products.forEach(product -> product.removeUser(this));
    }
}
