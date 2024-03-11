package org.example.code.controllers;

import lombok.AllArgsConstructor;
import org.example.code.entities.User;
import org.example.code.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @PostMapping("/{userId}/addProduct/{productId}")
    public ResponseEntity<List<User>> addProductToUser(@PathVariable Long userId, @PathVariable Long productId) {
        return userService.addProductToUser(userId, productId);
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getById/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}