package org.example.code.repositories;

import org.example.code.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "users")
    List<Product> findAll();
    @EntityGraph(attributePaths = "users")
    Optional<Product> findById(Long id);
}
