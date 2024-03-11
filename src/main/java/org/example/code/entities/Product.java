package org.example.code.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
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
        users.forEach(user -> user.removeProduct(this));
    }
}


