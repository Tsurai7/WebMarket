package org.example.code.repositories;

import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findById(Long id);
    void deleteById(Long id);
    boolean existsByName(String name);
    @Query("SELECT p FROM User u JOIN u.products p WHERE u.id = :userId AND p.id = :productId")
    Product findUserByIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
