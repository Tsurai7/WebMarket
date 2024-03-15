package org.example.code.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.code.entities.BankCard;
import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.repositories.BankCardRepository;
import org.example.code.repositories.ProductRepository;
import org.example.code.repositories.UserRepository;
import org.example.code.utilities.CustomCache;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final BankCardRepository bankCardRepository;

    private final CustomCache customCache;

    @Transactional
    public boolean addProductToUser(Long userId, Long productId) {

        User user = userRepository.findById(userId).orElse(null);

        Product product = productRepository.findById(productId).orElse(null);

        if (user != null && product != null) {
            user.addProduct(product);
            userRepository.save(user);

            return true;
        }

        return false;
    }

    @Transactional
    public boolean removeProductFromUser(Long userId, Long productId) {

        User user = userRepository.findById(userId).orElse(null);

        Product product = productRepository.findById(productId).orElse(null);

        if (user != null && product != null) {
            user.removeProduct(product);
            userRepository.save(user);

            return true;
        }

        return false;
    }

    public boolean addCard(Long userId, BankCard card) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            bankCardRepository.save(card);
            user.addCard(card);

            userRepository.save(user);

            return true;
        }

        return false;
    }

    public boolean removeCard(Long userId, Long cardId) {
        User user = userRepository.findById(userId).orElse(null);
        BankCard card = bankCardRepository.findById(cardId).orElse(null);

        if (user != null  && card != null) {
            user.removeCard(card);
            bankCardRepository.delete(card);

            return true;
        }

        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean register(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User userInDb = new User(user.getName(), user.getPassword());

        userRepository.save(userInDb);

        return true;
    }

    public User login(User user) {
        User userInDb = userRepository.findByName(user.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getPassword().equals(userInDb.getPassword())) {
            return userInDb;
        }

        else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public User update(Long id, User user) {
        return userRepository.findById(id).map(u -> {
                        u.setName(user.getName());
                        u.setPassword(user.getPassword());
                        return userRepository.save(u);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return false;
        }

        user.removeAllProducts();
        userRepository.deleteById(id);

        return true;
    }

    public Product getProductById(Long userId, Long productId) {

        if (userRepository.findById(userId).isPresent() && customCache.containsKey(productId.toString())) {
            return ((Product)customCache.getFromCache(productId.toString()));
        }

        else {
            Product product = userRepository.findUserByIdAndProductId(userId, productId);

            if (product == null) {
                return null;
            }

            customCache.addToCache(product.getId().toString(), product);
            return product;
        }
    }
}