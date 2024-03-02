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

    public User register(String name, String password) {

        if (userRepository.existsByName(name)) {
            throw new IllegalArgumentException("Username is already taken");
        }

        User user = new User(name, password);

        return userRepository.save(user);
    }

    public User login(String name, String password) {

        User user = userRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (password.equals(user.getPassword())) {
            return user;
        }
        else {
            throw new IllegalArgumentException("Invalid password");
        }

    }

}
