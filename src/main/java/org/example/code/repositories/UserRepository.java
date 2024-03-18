package org.example.code.repositories;

import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    void deleteById(Long id);
    @Query("SELECT p FROM User u JOIN u.products p WHERE u.id = :userId AND p.title = :title")
    Product findUserByIdAndProductName(@Param("userId") Long userId, @Param("title") String title);
    boolean findByName(String name);
}
