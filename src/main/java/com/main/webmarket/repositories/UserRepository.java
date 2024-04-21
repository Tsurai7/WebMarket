package com.main.webmarket.repositories;


import com.main.webmarket.entities.Product;
import com.main.webmarket.entities.User;
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
    Optional<Product> findProductByTitle(@Param("userId") Long userId, @Param("title") String title);
    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}
