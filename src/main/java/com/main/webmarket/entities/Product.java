package com.main.webmarket.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String category;

    @JsonBackReference
    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public Product(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    public void addUsers(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    @PreRemove
    public void removeAllUsers() {
        users.forEach(user -> user.getProducts().removeAll(Collections.singleton(this)));
    }
}
