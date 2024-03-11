package org.example.code.repositories;

import org.example.code.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> findByCustomQuery(@Param("category") String category);

}
