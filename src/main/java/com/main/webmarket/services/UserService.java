package com.main.webmarket.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.main.webmarket.entities.Product;
import com.main.webmarket.entities.User;
import com.main.webmarket.repositories.ProductRepository;
import com.main.webmarket.repositories.UserRepository;
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
        User user = userRepository.findById(userId)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        Product product = productRepository.findById(productId)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));

        user.addProduct(product);
        userRepository.save(user);
    }

    @Transactional
    public void addProductsToUser(Long userId, List<Long> productIds) {
        User user = userRepository.findById(userId)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        List<Product> products = productIds.stream()
            .map(productId -> productRepository.findById(productId)
                .orElseThrow(() ->
                    new NoSuchElementException(String.format("Product with ID %d not found", productId))))
            .collect(Collectors.toList());

        user.addProducts(products);
        userRepository.save(user);
    }

    @Transactional
    public void removeProductFromUser(Long userId, Long productId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, userId)));

        Product product = productRepository.findById(productId)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(PRODUCT_NOT_FOUND_MESSAGE, productId)));

        user.removeProduct(product);
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() ->
                new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    public User register(User user) {
        userRepository.findByName(user.getName()).ifPresent(existingUser -> {
            throw new IllegalArgumentException("User already exists");
        });

        return userRepository.save(new User(user.getName(), user.getPassword()));
    }

    public User update(Long id, User user) {
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setName(user.getName());
                existingUser.setPassword(user.getPassword());
                return userRepository.save(existingUser);
            })
            .orElseThrow(() -> new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException(String.format(USER_NOT_FOUND_MESSAGE, id)));

        user.removeAllProducts();
        userRepository.deleteById(id);
    }
}