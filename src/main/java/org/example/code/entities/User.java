package org.example.code.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_products",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @JsonBackReference
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "holder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<BankCard> bankCards = new HashSet<>();


    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
