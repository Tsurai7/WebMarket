package com.main.webmarket.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import com.main.webmarket.entities.Product;
import com.main.webmarket.entities.User;
import com.main.webmarket.repositories.ProductRepository;
import com.main.webmarket.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"checkstyle:MissingJavadocType", "checkstyle:MissingJavadocMethod"})
public class UserService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private static final String USER_NOT_FOUND_MESSAGE = "User not found with id: %d";

    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found with id: %d";

    @Autowired
    public UserService(
            UserRepository userRepository,
            ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProductToUser(Long userId, Long productId) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId));
                                });

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException(
                                            String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
                                });

        user.addProduct(product);
        userRepository.save(user);
    }

    @Transactional
    public void removeProductFromUser(Long userId, Long productId) {

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId));
                                });

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException(
                                            String.format(PRODUCT_NOT_FOUND_MESSAGE, productId));
                                });


        user.removeProduct(product);
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(
                        () -> {
                            throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id));
                        });
    }

    public boolean register(User user) {

        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        User userInDb = new User(user.getName(), user.getPassword());
        userRepository.save(userInDb);
        return true;
    }

    public User update(Long id, User user) {
        return userRepository
                .findById(id)
                .map(
                        u -> {
                            u.setName(user.getName());
                            u.setPassword(user.getPassword());

                            return userRepository.save(u);
                        })
                .orElseThrow(
                        () -> {
                            throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id));
                        });
    }

    public void delete(Long id) {
        User user =
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id));
                                });

        user.removeAllProducts();
        userRepository.deleteById(id);
    }
}

