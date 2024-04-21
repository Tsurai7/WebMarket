package com.main.webmarket.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String image;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "holder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<BankCard> bankCards = new HashSet<>();

    public User(String name, String image, String email, String password) {
        this.name = name;
        this.image = image;
        this.email = email;
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

    public void removeProduct(Product product) {
        products.remove(product);
        product.getUsers().remove(this);
    }

    @PreRemove
    public void removeAllProducts() {
        products.forEach(product -> product.removeUser(this));
    }

    public void addProducts(List<Product> products) {
        products.forEach(this::addProduct);
    }
}
