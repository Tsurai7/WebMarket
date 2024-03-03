package org.example.code.services;

import lombok.AllArgsConstructor;
import org.example.code.entities.User;
import org.example.code.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User not found"));
    }

    public User register(User user) {
        if (userRepository.existsByName(user.getName())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User userInDb = new User(user.getName(), user.getPassword());

        return userRepository.save(userInDb);
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
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}