package org.example.code.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.code.entities.Product;
import org.example.code.entities.User;
import org.example.code.repositories.ProductRepository;
import org.example.code.repositories.UserRepository;
import org.example.code.utilities.CustomCache;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean register(User user) {

        if (userRepository.existsByName(user.getName())) {
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