package org.example.code.services;

import jakarta.transaction.Transactional;
import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.repositories.ProductRepository;
import org.example.code.repositories.UserRepository;
import org.example.code.utilities.CustomCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final CustomCache customCache;

    @Autowired
    public UserService(UserRepository userRepository, ProductRepository productRepository,
                       CustomCache customCache) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.customCache = customCache;
    }

    @Transactional
    public void addProductToUser(Long userId, Long productId) {

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with id: {}", userId);
            throw new NoSuchElementException("User not found with id: " + userId);
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            logger.error("Product not found with id: {}", userId);
            throw new NoSuchElementException("Product not found with id: " + userId);
        });

        user.addProduct(product);
        userRepository.save(user);
    }

    @Transactional
    public void removeProductFromUser(Long userId, Long productId) {

        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User not found with id: {}", userId);
            throw new NoSuchElementException("User not found with id: " + userId);
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> {
            logger.error("Product not found with id: {}", productId);
            throw new NoSuchElementException("Product not found with id: " + productId);
        });

        user.removeProduct(product);
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    throw new NoSuchElementException("User not found with id: " + id);
                });
    }

    public boolean register(User user) {

        if (userRepository.findByName(user.getName())) {
            return false;
        }

        User userInDb = new User(user.getName(), user.getPassword());
        userRepository.save(userInDb);
        return true;
    }

    public User update(Long id, User user) {
        return userRepository.findById(id).map(u -> {
            u.setName(user.getName());
            u.setPassword(user.getPassword());

            return userRepository.save(u);
        }).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            throw new NoSuchElementException("User not found with id: " + id);
        });
    }

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.error("User not found with id: {}", id);
            throw new NoSuchElementException("User not found with id: " + id);
        });

        user.removeAllProducts();
        userRepository.deleteById(id);
    }

    public Product getProductById(Long userId, String title) {

        if (userRepository.findById(userId).isPresent() && customCache.containsKey(title)) {
            return ((Product)customCache.getFromCache(title));
        }

        else if (userRepository.findById(userId).isPresent()){
            Product product = userRepository.findUserByIdAndProductName(userId, title);

            if (product == null) {
                return null;
            }

            customCache.addToCache(product.getId().toString(), product);
            return product;
        }
        else {

            if (customCache.containsKey(title)) {
                customCache.removeFromCache(title);
            }
            return null;
        }
    }
}