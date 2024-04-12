package com.main.webmarket.repositories;

import java.util.List;
import java.util.Optional;
import com.main.webmarket.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("checkstyle:MissingJavadocType")
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);
}
