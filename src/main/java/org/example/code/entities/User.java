package org.example.code.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    //@ManyToMany(mappedBy = "users")
    //private Set<Stock> stocks;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
